package com.inventory.web.startup;

import com.inventory.core.api.iapi.ISendMailSSL;
import com.inventory.core.model.entity.*;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.*;
import com.inventory.core.util.Authorities;
import com.inventory.core.util.ParseUtls;
import com.inventory.core.util.ShellUtls;
import com.inventory.web.util.ParameterConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Startup {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    @Autowired
    private StateInfoRepository stateInfoRepository;

    @Autowired
    private CityInfoRepository cityInfoRepository;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ISendMailSSL sendMailSSL;

    public void initialize() throws Exception {

        if (!ParseUtls.isValidMacAddress(new String[]{"54:e1:ad:53:17:06" , "f8:28:19:c7:eb:47"})){
            sendMailSSL.sendMail("inventory.sys.info@gmail.com" , "dhirajbadu50@gmail.com", "war provided to Dev Raj Three Monks" , "Alert : some one trying to install on unverified server" );
            throw new Exception("invalid mac Address");
        }

        createUser("system", UserType.SYSTEM, "123456", Status.ACTIVE);

        CountryStarter();

        createService();

        //System.out.println(" count lot >>>>>> " + lotInfoRepository.count());
        if (lotInfoRepository.count() == 0)
            lotCreater();

        if (designationRepository.count() == 0)
            designationCreater();
    }

    private void createUser(String userName, UserType userType, String password, Status status) {
        try {
            User user = userRepository.findByUsername(userName);

            if (user == null) {
                User dto = new User();

                dto.setUsername(userName);
                dto.setUserType(userType);
                dto.setPassword(passwordEncoder.encode(password));
                dto.setStatus(status);
                dto.setEnabled(true);
                dto.setAuthority(Authorities.SYSTEM + "," + Authorities.AUTHENTICATED);

                userRepository.save(dto);
            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    private CountryInfo createCountry(String name, String ISO, Status status) {
        CountryInfo countryInfo = countryInfoRepository.findByName(name);
        CountryInfo dto = new CountryInfo();
        if (countryInfo == null) {

            dto.setName(name);
            dto.setISO(ISO);
            dto.setStatus(status);

            dto = countryInfoRepository.save(dto);
        }

        return dto;
    }


    public void CountryStarter() {
        CountryInfo c = createCountry("Nepal", "NPL", Status.ACTIVE);
        HashMap<CountryInfo, String> statesmap = new HashMap<CountryInfo, String>();

        statesmap.put(c,
                "Mechi,Koshi,Sagarmatha,Janakpur,Bagmati,Narayani,Gandaki,Lumbini,Dhaulagiri,Rapti,Karnali,Bheri,Seti,Mahakali");
        List<StateInfo> s = createState(statesmap);

        List<String> cityMap = new ArrayList<String>();
        cityMap.add("Ilam,Jhapa,Panchthar,Taplejung");
        cityMap.add("Bhojpur,Dhankuta,Morang,Sankhuwasabha,Sunsari,Terhathum");
        cityMap.add("Khotang,Okhaldhunga,Saptari,Siraha,Solukhumbu,Udayapur");
        cityMap.add("Dhanusa,Dholkha,Mahottari,Ramechhap,Sarlahi,Sindhuli");
        cityMap.add("Bhaktapur,Dhading,Kathmandu,Kavrepalanchok,Lalitpur,Nuwakot,Rasuwa,Sindhupalchok ");
        cityMap.add("Bara,Chitwan,Makwanpur,Parsa,Rautahat");
        cityMap.add("Gorkha,Kaski,Lamjung,Manang,Syangja,Tanahu");
        cityMap.add("Arghakhanchi,Gulmi,Kapilvastu,Nawalparasi,Palpa,Rupandehi");
        cityMap.add("Baglung,Mustang,Myagdi,Parbat");
        cityMap.add("Dang,Pyuthan,Rolpa,Rukum,Salyan");
        cityMap.add("Dolpa,Humla,Jumla,Kalikot,Mugu");
        cityMap.add("Banke,Bardiya,Dailekh,Jajarkot,Surkhet");
        cityMap.add("Achham,Bajhang,Bajura,Doti,Kailali");
        cityMap.add("Baitadi,Dadeldhura,Darchula,Kanchanpur");
        HashMap<StateInfo, String> citymap = new HashMap<StateInfo, String>();
        int start = 0;
        for (StateInfo ss : s) {
            citymap.put(ss, cityMap.get(start));
            start++;
        }
        createCity(citymap);

    }

    private List<StateInfo> createState(HashMap<CountryInfo, String> statesmap) {
        List<StateInfo> st = new ArrayList<StateInfo>();
        for (Map.Entry<CountryInfo, String> entry : statesmap.entrySet()) {

            CountryInfo c = entry.getKey();
            String[] values = entry.getValue().split(",");
            for (int i = 0; i < values.length - 1; i++) {

                StateInfo s = stateInfoRepository.findByName(values[i]);
                if (s == null) {
                    s = new StateInfo();
                    s.setCountryInfo(c);
                    s.setName(values[i]);
                    s.setStatus(Status.ACTIVE);
                    s = stateInfoRepository.save(s);
                    st.add(s);
                }

            }
        }
        return st;
    }

    private void createCity(HashMap<StateInfo, String> citymap) {
        for (Map.Entry<StateInfo, String> entry : citymap.entrySet()) {

            StateInfo s = entry.getKey();
            String[] values = entry.getValue().split(",");
            for (int i = 0; i < values.length - 1; i++) {

                CityInfo c = cityInfoRepository.findByName(values[i]);
                if (c == null) {
                    c = new CityInfo();
                    c.setStateInfo(s);
                    c.setName(values[i]);
                    c.setStatus(Status.ACTIVE);
                    cityInfoRepository.save(c);
                }

            }
        }
    }

    private void lotCreater() {

        List<LotInfo> lotInfoList = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            LotInfo lotInfo = new LotInfo();

            if (i == 1) {
                lotInfo.setLot(i + " st lot");
            } else if (i == 2) {
                lotInfo.setLot(i + " nd lot");
            } else if (i == 3) {
                lotInfo.setLot(i + " rd lot");
            } else {
                lotInfo.setLot(i + " th lot");
            }

            lotInfo.setStatus(Status.ACTIVE);
            lotInfoList.add(lotInfo);
        }

        lotInfoRepository.save(lotInfoList);

    }

    private void designationCreater() {

        List<Designation> designationList = new ArrayList<>();

        Designation maneger = new Designation();

        maneger.setTitle("Managing Director");
        maneger.setCode("MD");
        maneger.setRemarks("Managing Director of this store");

        designationList.add(maneger);

        Designation marketingManager = new Designation();

        marketingManager.setTitle("Marketing Manager");
        marketingManager.setCode("MM");
        marketingManager.setRemarks("Team leader of marketing officers");

        designationList.add(marketingManager);

        Designation marketingOfficer = new Designation();

        marketingOfficer.setTitle("Marketing Officer");
        marketingOfficer.setCode("MO");
        marketingOfficer.setRemarks("marketing officer");

        designationList.add(marketingOfficer);

        Designation accountant = new Designation();

        accountant.setTitle("Accountant");
        accountant.setCode("AT");
        accountant.setRemarks("Accountant");

        designationList.add(accountant);

        designationRepository.save(designationList);
    }

    private void createService(){

        if (serviceRepository.findByTitle("demo") == null){
            saveService("demo" , 30 , 0 , 1);
        }
    }

    private void saveService(String title , int expDays , double rate , int totalStore){

        ServiceInfo serviceInfo = new ServiceInfo();

        serviceInfo.setTitle(title);
        serviceInfo.setExpireDays(expDays);
        serviceInfo.setRate(rate);
        serviceInfo.setStatus(Status.ACTIVE);
        serviceInfo.setTotalStore(totalStore);

        serviceRepository.save(serviceInfo);
    }

}
