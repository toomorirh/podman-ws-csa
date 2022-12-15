package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Tag(name = "Sample", description = "Sample Rest API")
public class SampleController {

    @Operation(summary = "疎通応答")
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @Operation(summary = "データ取得", description = "対象となるidのデータを取得する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "404", description = " データが存在しません")
    })
    @GetMapping("/get/{id}")
    public Map<String, String> getData(
        @Parameter(required = true, description = "データのID", example = "1234")
        @PathVariable String id){        
        return Map.of(
                "id", id,
                "name", "SampleName",
                "value", "SampleValue");
    }

    @Operation(summary = "データ追加", description = "データを追加する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "405", description = " 許可されないメソッドです")
    })
    @PostMapping("/create")
    public Map<String, String> createData(
        @RequestParam(name = "id", value = "id", defaultValue = "1234") String id,
        @RequestParam(name = "name", value = "name", defaultValue = "sampleName") String name,
        @RequestParam(name = "value", value = "value", defaultValue = "sampleValue") String value
        ){

        return Map.of(
            "id", id,
            "name", name,
            "value", value );
    }
}