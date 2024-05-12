//package com.earnwise.api.utils;
//
//import com.earnwise.api.domain.dto.CreateExpertProfileRequest;
//import com.earnwise.api.domain.model.ExpertProfile;
//import com.earnwise.api.repository.ExpertProfileRepository;
//import com.earnwise.api.service.ExpertProfileService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class ExpertConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(
//            ExpertProfileService service
//    ) {
//        return args -> {
//            CreateExpertProfileRequest request = new CreateExpertProfileRequest();
//            request.setCategory("Tech");
//            request.setTitle("Narcissistic Personality Disorder");
//            request.setDescription("Better investment than any coaching program. Highly recommend!!");
//            List<String> topics = new ArrayList<>();
//            topics.add("Business");
//            topics.add("Career");
//            topics.add("Entertainment");
//
//            request.setTopics(topics);
//            request.setHourlyRate(100.0);
//            request.setUserId("85be89ce-9bfd-4f9b-9ab1-e9fb2311b5eb");
//            request.setCoverImage("");
//            service.create(request);
//
//
//            CreateExpertProfileRequest request2 = new CreateExpertProfileRequest();
//            request2.setCategory("Business");
//            request2.setTitle("Disorder");
//            request2.setDescription("Absolutely!!!! For the past 2 years I have paid money for coaches and programs that have been a ton of work and ");
//
//            List<String> topics2 = new ArrayList<>();
//            topics2.add("Travel");
//            topics2.add("Education");
//            topics2.add("Social Impact");
//
//            request2.setTopics(topics);
//            request2.setHourlyRate(90.0);
//            request2.setUserId("2e02cb28-9001-4d73-8799-b52b5dd9e7f6");
//            request2.setCoverImage("");
//            service.create(request2);
//
//        };
//    }
//}
