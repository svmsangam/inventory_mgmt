package com.inventory.web.startup;

import com.inventory.core.model.entity.CityInfo;
import com.inventory.core.model.entity.CountryInfo;
import com.inventory.core.model.entity.StateInfo;
import com.inventory.core.model.entity.User;
import com.inventory.core.model.enumconstant.Status;
import com.inventory.core.model.enumconstant.UserType;
import com.inventory.core.model.repository.CityInfoRepository;
import com.inventory.core.model.repository.CountryInfoRepository;
import com.inventory.core.model.repository.StateInfoRepository;
import com.inventory.core.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
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

	@PostConstruct
	public void initialize() {

		createUser("system" , UserType.SYSTEM , "123456" , Status.ACTIVE);

		CountryStarter();
	}

	private void createUser(String userName, UserType userType,  String password, Status status) {
		try {
			User user = userRepository.findByUsername(userName);
			
			if (user==null) {
				User dto = new User();

				dto.setUsername(userName);
				dto.setUserType(userType);
				dto.setPassword(passwordEncoder.encode(password));
				dto.setStatus(status);
				dto.setEnabled(true);

				userRepository.save(dto);
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}

	private CountryInfo createCountry(String name, String ISO,  Status status) {
			CountryInfo countryInfo = countryInfoRepository.findByName(name);
			CountryInfo dto = new CountryInfo();;
			if (countryInfo==null) {

				dto.setName(name);
				dto.setISO(ISO);
				dto.setStatus(status);

				dto = countryInfoRepository.save(dto);
			}

			return dto;
	}


	public void CountryStarter() {
		CountryInfo c = createCountry("Nepal" , "NPL" , Status.ACTIVE);
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

}
