package com.inventory.core.api.impl;

import com.inventory.core.api.iapi.IUserApi;
import com.inventory.core.model.converter.UserConverter;
import com.inventory.core.model.dto.InvUserDTO;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.UserPermission;
import com.inventory.core.model.entity.VerificationToken;
import com.inventory.core.model.enumconstant.Permission;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.*;
import com.inventory.core.util.Authorities;
import com.inventory.web.session.UserDetailsServiceImpl;
import com.inventory.web.session.UserDetailsWrapper;
import netscape.security.Privilege;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class UserApi implements IUserApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @Autowired
    private StoreUserInfoRepository storeUserInfoRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public long save(String username , String password){

        User entity = new User();

        entity.setUsername(username.trim().toLowerCase());
        entity.setStatus(Status.INACTIVE);
        entity.setUserType(UserType.SUPERADMIN);
        entity.setEnabled(true);
        entity.setAuthority(Authorities.SUPERADMIN + "," + Authorities.AUTHENTICATED);
        entity.setPassword(passwordEncoder.encode(password.trim()));

        entity = userRepository.save(entity);

        return entity.getId();

    }

    @Override
    public long getTotalUserByStoreInfoAndStatus(long storeInfoId, Status status) {
        return userRepository.countAllByStoreInfoAndStatus(storeInfoId , status);
    }

    @Override
    public InvUserDTO save(InvUserDTO userDTO) throws IOException, JSONException {

        User user = userConverter.convertToEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);

        return userConverter.convertToDto(user);
    }

    public void changePassword(long userId, String newPassword) throws IOException, JSONException {

        User user = userRepository.findOne(userId);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public InvUserDTO getUserWithId(long userId) {
        return userConverter.convertToDto(userRepository.findByIdAndStatus(userId, Status.ACTIVE));

    }

    @Override
    public InvUserDTO getUserPermission(InvUserDTO userDTO) {

        UserPermission userPermission = userPermissionRepository.findByPermissionUser(userDTO.getUserId());

        if (userPermission != null)
            userDTO.setPermissionList(userPermission.getPermissionList());

        return userDTO;
    }

    @Override
    public List<Permission> getUserPermission(long userId) {

        UserPermission userPermission = userPermissionRepository.findByPermissionUser(userId);

        if (userPermission != null)
            return userPermission.getPermissionList();

        return null;

    }

    @Override
    public InvUserDTO updateEnable(long userId) {

        User user = userRepository.findById(userId);

        if (user.getEnabled()) {
            user.setEnabled(false);

        } else {
            user.setEnabled(true);
        }

        return userConverter.convertToDto(userRepository.save(user));
    }

    @Override
    public InvUserDTO getUserByUserName(String userName) {

        User user = userRepository.findByUsername(userName);

        return userConverter.convertToDto(user);
    }

    @Override
    public boolean nameExists(String userName) {
        return userRepository.findByUsername(userName) != null;
    }

    @Override
    public void updateFCMToken(String token, long userId) {

        synchronized (this) {

            User user = userRepository.findById(userId);

            if (!token.equals(user.getFcmKey())) {

                user.setFcmKey(token);

                userRepository.save(user);
            }
        }
    }

    @Override
    public InvUserDTO changeStore(long userId, long storeId) {

        User user = userRepository.findById(userId);

        user.setStoreInfo(storeInfoRepository.findById(storeId));

        return userConverter.convertToDto(userRepository.save(user));
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeIn(Status status, List<UserType> userTypeList) {
        return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeIn(status , userTypeList));
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeInAndSuperAdmin(Status status, List<UserType> userTypeList , long userId) {

        List<Long> storeList = storeUserInfoRepository.findAllStoreIdByUserAndStatus(userId , Status.ACTIVE);

        if (storeList == null){
            storeList = new ArrayList<>();

            storeList.add((long)0);
        }

        return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeInAndSuperAdmin(status, userTypeList , storeList));
    }

    @Override
    public List<InvUserDTO> getAllByStatusAndUserTypeInAndStoreInfo(Status status, List<UserType> userTypeList, long storeInfoId) {
        return userConverter.convertToDtoList(userRepository.findAllByStatusAndUserTypeInAndStoreInfo(status, userTypeList, storeInfoId));
    }

    @Override
    public String saveVerificationToken(long userId){

        User user = userRepository.findById(userId);

        VerificationToken verificationToken = new VerificationToken();

        String token = UUID.randomUUID().toString();

        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;

    }

    @Override
    public InvUserDTO getByToken(String token){

        return userConverter.convertToDto(verificationTokenRepository.findUserByToken(token));
    }

    @Override
    @Transactional
    public void verifyUser(String token, HttpServletRequest request){

        User user = verificationTokenRepository.findUserByToken(token);

        if (user != null){
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);

            authenticateUserAndSetSession(user , request);
            verificationTokenRepository.deleteByToken(token);
        }

    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {

        String username = user.getUsername();
        String password = user.getPassword();

        UserDetailsServiceImpl service = new UserDetailsServiceImpl(userRepository);

        UserDetailsWrapper wrapper  = (UserDetailsWrapper) service.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(wrapper, password, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthority()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /*public void authWithoutPassword(User user){
        List<Privilege> privileges = user.getAuthority().stream()
                .map(role -> role.getPrivileges())
                .flatMap(list -> list.stream())
                .distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = privileges.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/

}
