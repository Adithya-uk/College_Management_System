package com.assessment.college_management_system.service;

import com.assessment.college_management_system.entity.College;
import com.assessment.college_management_system.repositories.CollegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;

    private static final String INVALID_ID_EXCEPTION_MESSAGE = "Invalid ID";

    public List<College> findAll(Pageable pageable) {
        log.info("Getting details of all the colleges");
        Page<College> pagingResult = collegeRepository.findAll(pageable);
        log.info("Returning details of all the colleges");
        return pagingResult.getContent();
    }


    public College findById(long id) {
        log.info("Searching for college with id = " + id);
//        return collegeRepository.findById(id).orElseThrow(() ->new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE));
        Optional<College> result = collegeRepository.findById(id);
        if (result.isPresent()) {
            log.info("college with id = " + id + "found");
            return result.get();
        }
        log.error(INVALID_ID_EXCEPTION_MESSAGE);
        throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
    }

    public College save(College college) {
        try {
            log.info(("Saving college"));
            return collegeRepository.save(college);
        } catch (Exception e) {
            throw new RuntimeException("Duplicate department tried to be inserted for college");
        }
    }

    public College update(College college) {
        log.info(("Updating college"));
        try {
            return collegeRepository.save(college);

        } catch (Exception e) {
            log.error("Update unsuccessful");
            throw new RuntimeException("Duplicate department for the college");
        }
    }


    public void delete(long id) {

        try {
            collegeRepository.deleteById(id);
        } catch (RuntimeException exception) {
            log.error(INVALID_ID_EXCEPTION_MESSAGE);
            throw new RuntimeException(INVALID_ID_EXCEPTION_MESSAGE);
        }

    }

}
