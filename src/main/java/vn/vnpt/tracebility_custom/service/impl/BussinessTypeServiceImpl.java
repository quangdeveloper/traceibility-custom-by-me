package vn.vnpt.tracebility_custom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.tracebility_custom.entity.BussinessType;
import vn.vnpt.tracebility_custom.mapper.BusinessTypeMapper;
import vn.vnpt.tracebility_custom.model.response.BusinessTypeRP;
import vn.vnpt.tracebility_custom.model.response.PageRP;
import vn.vnpt.tracebility_custom.repository.BussinessTypeRepository;
import vn.vnpt.tracebility_custom.service.BussinessTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BussinessTypeServiceImpl implements BussinessTypeService {

    @Autowired
    private BussinessTypeRepository bussinessTypeRepository;

    @Autowired
    private BusinessTypeMapper businessTypeMapper;

    @Override
    public PageRP findAll() {

        final List<BusinessTypeRP> list = bussinessTypeRepository.findAll()
                .stream()
                .map(businessTypeMapper::toBusinessTypeRPFromBT)
                .collect(Collectors.toList());

        return PageRP.builder()
                .total(list.size())
                .content(list)
                .build();
    }
}
