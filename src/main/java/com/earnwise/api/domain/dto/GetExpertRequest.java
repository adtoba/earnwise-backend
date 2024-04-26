package com.earnwise.api.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetExpertRequest {
    List<String> topics = new ArrayList<>();
}
