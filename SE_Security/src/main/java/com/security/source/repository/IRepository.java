package com.security.source.repository;

import com.security.source.dto.FilterResponseDTO;
import com.security.source.dto.SecurityPolicyDTO;

import java.util.List;

public interface IRepository {

    boolean save(List<SecurityPolicyDTO> securityPolicyDTO);

    String filter(FilterResponseDTO filterResponseDTO);

}
