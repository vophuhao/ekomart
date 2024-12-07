package vn.iotstar.service.user.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Address;
import vn.iotstar.entity.UserInfo;
import vn.iotstar.repository.AddressRepository;
import vn.iotstar.service.user.IAddressService;

@Service
public class AddressServiceImp implements IAddressService{

	@Autowired
	AddressRepository addressrepo;
	 public Address save(Address address) {
	        return addressrepo.save(address);  // Lưu địa chỉ vào CSDL
	    }
	@Override
	public List<Address> findByUser(UserInfo user) {
		// TODO Auto-generated method stub
		return addressrepo.findByUser(user);
	}
	@Override
	public Optional<Address> findById(Long id) {
		// TODO Auto-generated method stub
		return addressrepo.findById(id);
	}
	
	@Override
	public  void delete(Long id)
	{
		addressrepo.deleteById(id);
	}
	@Override
	public Optional<Address> findByDefaults(int defaults) {
		return addressrepo.findByDefaults(defaults);
	}
	@Override
	public Optional<Address> findByUserAndDefaults(UserInfo user, int defaults) {
		// TODO Auto-generated method stub
		return addressrepo.findByUserAndDefaults(user, defaults);
	}
	
	
}
