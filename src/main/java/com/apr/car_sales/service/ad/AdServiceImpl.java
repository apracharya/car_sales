package com.apr.car_sales.service.ad;

import com.apr.car_sales.exception.ResourceNotFoundException;
import com.apr.car_sales.persistence.ad.AdEntity;
import com.apr.car_sales.persistence.ad.AdRepository;
import com.apr.car_sales.persistence.category.CategoryEntity;
import com.apr.car_sales.persistence.category.CategoryRepository;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AdServiceImpl(AdRepository adRepository,
                         CategoryRepository categoryRepository,
                         UserRepository userRepository,
                         ModelMapper modelMapper) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AdModel createCarAd(AdModel adModel, int categoryId, int sellerId) {

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        UserEntity seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", "user id", sellerId));

        AdEntity entity = modelMapper.map(adModel, AdEntity.class);
        entity.setCategory(category);
        entity.setSeller(seller);

        AdEntity created = adRepository.save(entity);
        return modelMapper.map(created, AdModel.class);
    }


    @Override
    public AdModel readCarAd(int id) {
        AdEntity ad = adRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ad", "ad_id", id));
        return modelMapper.map(ad, AdModel.class);
    }

    @Override
    public List<AdModel> readAllCarsAd() {
        List<AdEntity> ads = adRepository.findAll();
        return ads.stream()
                .map(ad -> modelMapper.map(ad, AdModel.class))
                .toList();
    }

    @Override
    public AdModel updateCarAd(AdModel carModel, int id) {
        return null;
    }

    @Override
    public void deleteCarAd(int id) {
        adRepository.deleteById(id);
    }
}
