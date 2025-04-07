package ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelsDtoMsg {

    private List<HotelDtoMsg> hotels;

}
