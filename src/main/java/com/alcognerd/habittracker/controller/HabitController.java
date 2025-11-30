package com.alcognerd.habittracker.controller;

import com.alcognerd.habittracker.dto.ApiResponse;
import com.alcognerd.habittracker.dto.HabitCreate;
import com.alcognerd.habittracker.dto.HabitOut;
import com.alcognerd.habittracker.model.Habit;
import com.alcognerd.habittracker.model.User;
import com.alcognerd.habittracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping()
    public ResponseEntity<ApiResponse<HabitOut>> createHabit(@RequestBody HabitCreate habitRequest, Authentication authentication){
        try{
            User user  =(User) authentication.getPrincipal();
            if(user == null){
                throw new RuntimeException("User not found");
            }
            HabitOut habitOut = habitService.createHabit(habitRequest,user);
            ApiResponse<HabitOut> apiResponse = new ApiResponse<>(HttpStatus.CREATED.name(), "Habit added successfully",habitOut);
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e){
            e.printStackTrace();
            ApiResponse<HabitOut> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.name(), e.getMessage());
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<HabitOut>>> getHabits(Authentication authentication){
        try {
            User user = (User) authentication.getPrincipal();
            List<HabitOut> habitOutList = habitService.getAllHabits(user);
            ApiResponse<List<HabitOut>> apiResponse = new ApiResponse<>(HttpStatus.OK.name(), "Fetched user habits successfully", habitOutList);
            return ResponseEntity.ok(apiResponse);
        }catch (Exception e){
            ApiResponse<List<HabitOut>> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.name(), e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<HabitOut>> updateStatus(@RequestBody Habit habit, @RequestBody String status, Authentication authentication){
        try{
            User user  =(User) authentication.getPrincipal();

        HabitOut habitOut = habitService.updateTodayHabitStatus(user,status,habit);
        ApiResponse<HabitOut> apiResponse = new ApiResponse<>(HttpStatus.OK.name(), "Habit updated successfully",habitOut);
        return ResponseEntity.ok(apiResponse);}
        catch (Exception e){
            ApiResponse<HabitOut> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.name(), e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<HabitOut>>> getTodayHabits(Authentication authentication){
        try{
            User user  =(User) authentication.getPrincipal();

        List<HabitOut> habitOuts = habitService.getTodayHabits(user);
        ApiResponse<List<HabitOut>> apiResponse = new ApiResponse<>(HttpStatus.OK.name(), "Fetched today habits successfully",habitOuts);
        return ResponseEntity.ok(apiResponse);}
        catch (Exception e){
            ApiResponse<List<HabitOut>> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.name(), e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }


}
