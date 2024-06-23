package com.dushanz.bookmanager.service;

import com.dushanz.bookmanager.dto.BorrowerDTO;
import com.dushanz.bookmanager.entity.Borrower;
import com.dushanz.bookmanager.mapper.BorrowerMapper;
import com.dushanz.bookmanager.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for various CRUD operations for the Borrowers in the system.
 */
@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    /**
     * Register a new borrower in the system and returns the created record
     *
     * @return newly registered Borrower
     */
    public BorrowerDTO registerBorrower(BorrowerDTO borrower) {
        Borrower newBorrower = BorrowerMapper.INSTANCE.borrowerDTOToBorrower(borrower);
        return BorrowerMapper.INSTANCE.borrowerToBorrowerDTO(borrowerRepository.save(newBorrower));

    }

    public List<BorrowerDTO> getAllBorrowers() {
        List<Borrower> borrowers = borrowerRepository.findAll();
        return borrowers.stream()
                .map(BorrowerMapper.INSTANCE::borrowerToBorrowerDTO)
                .collect(Collectors.toList());
    }

}
