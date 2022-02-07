package ex.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ex.sample.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
@Autowired
	private AdministratorRepository administratorRepository;



}
