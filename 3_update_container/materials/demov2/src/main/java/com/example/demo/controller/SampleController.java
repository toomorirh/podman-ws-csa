package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CreateDataParam;
import com.example.demo.model.UpdateDataParam;


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
        @Parameter(required = true, description = "データのID", example = "1234") @PathVariable String id){        
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
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
            @ExampleObject(name = "sample001", value = "{ \"id\": \"1234\", \"name\": \"sampleName\",  \"value\": \"sampleValue\" }"),
            @ExampleObject(name = "sample002", value = "{ \"id\": \"5678\", \"name\": \"sampleName2\",  \"value\": \"sampleValue2\" }")
        })) CreateDataParam createParam
        ){
        
        return Map.of(
            "id", createParam.getId(),
            "name", createParam.getName(),
            "value", createParam.getName() );
    }

    @Operation(summary = "データ更新", description = "データを更新する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "405", description = " 許可されないメソッドです")
    })
    @PutMapping("/update/{id}")
    public Map<String, String> updateData(
        @Parameter(required = true, description = "データのID", example = "1234") @PathVariable String id,
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = {
            @ExampleObject(name = "sample001", value = "{ \"name\": \"sampleName\",  \"value\": \"sampleValue\" }"),
            @ExampleObject(name = "sample002", value = "{ \"name\": \"sampleName2\",  \"value\": \"sampleValue2\" }")
        })) UpdateDataParam updateParam
        ){

        return Map.of(
            "id", id,
            "name", updateParam.getName(),
            "value", updateParam.getValue() );
    }

    @Operation(summary = "データ削除", description = "データを削除する")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "405", description = " 許可されないメソッドです")
    })
    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteData(
        @Parameter(required = true, description = "データのID", example = "1234") @PathVariable String id ){

        return Map.of(
            "result","success");
    }
}