package com.reliaquest.api.service;

import com.reliaquest.api.entity.Employee;
import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.exception.APIException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import com.reliaquest.api.feign.EmployeeFeignClient;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.reliaquest.api.dataprovider.EmployeeDataProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class IEmployeeServiceImplTest {
    @InjectMocks
    private IEmployeeServiceImpl iEmployeeService;

    @Mock
    private EmployeeFeignClient employeeFeignClient;

    private final Request request = Request.create(Request.HttpMethod.GET, "url",
            new HashMap<>(), null, new RequestTemplate());

    @Test()
    void testGetAllEmployees() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<Employee> allEmployees = iEmployeeService.getAllEmployees();
        assertFalse(CollectionUtils.isEmpty(allEmployees));
        assertEquals(2, allEmployees.size());
    }

    @Test
    void testGetAllEmployeesWhenNoRecordsFound() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getAllEmployees());
        assertEquals(ErrorCode.NO_RECORDS_FOUND, apiException.getErrorCode());
    }

    @Test()
    void testGetAllEmployeesWhenFeignClientThrowsException() {
        when(employeeFeignClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getAllEmployees());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<Employee> employeesByNameSearch = iEmployeeService.getEmployeesByNameSearch("Sanjivani");
        assertFalse(CollectionUtils.isEmpty(employeesByNameSearch));
        assertEquals(1, employeesByNameSearch.size());
        assertEquals("Sanjivani", employeesByNameSearch.get(0).getName());
    }

    @Test
    void testGetEmployeesByNameSearchWhenNoRecordsFound() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeesByNameSearch("John1"));
        assertEquals(ErrorCode.NO_RECORDS_FOUND, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeesByNameSearchWhenFeignClientThrowsException() {
        when(employeeFeignClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getEmployeesByNameSearch("John"));
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeFeignClient.getEmployeeById(UUID.fromString("a4d7ea02-e9fd-42b8-88bb-33ba9eca76ca"))).thenReturn(getEmployeeResponse());
        Employee employee = iEmployeeService.getEmployeeById("a4d7ea02-e9fd-42b8-88bb-33ba9eca76ca");
        assertEquals("Sanjivani", employee.getName());
    }


    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        Integer highestSalaryOfEmployees = iEmployeeService.getHighestSalaryOfEmployees();
        assertEquals(10000, highestSalaryOfEmployees);
    }

    @Test
    void testGetHighestSalaryOfEmployeesWhenNoRecordsFound() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        Integer highestSalaryOfEmployees = iEmployeeService.getHighestSalaryOfEmployees();
        assertEquals(0, highestSalaryOfEmployees);
    }

    @Test
    void testGetHighestSalaryOfEmployeesWhenFeignClientThrowsException() {
        when(employeeFeignClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getHighestSalaryOfEmployees());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmployeesResponse());
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        assertFalse(CollectionUtils.isEmpty(topTenHighestEarningEmployeeNames));
        assertEquals(2, topTenHighestEarningEmployeeNames.size());
        assertEquals("Sanjivani", topTenHighestEarningEmployeeNames.get(0));
        assertEquals("Shriraj", topTenHighestEarningEmployeeNames.get(1));
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNamesWhenNoRecordsFound() {
        when(employeeFeignClient.getAllEmployees()).thenReturn(getEmptyEmployeeResponse());
        List<String> topTenHighestEarningEmployeeNames = iEmployeeService.getTopTenHighestEarningEmployeeNames();
        assertTrue(CollectionUtils.isEmpty(topTenHighestEarningEmployeeNames));
        assertEquals(0, topTenHighestEarningEmployeeNames.size());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNamesWhenFeignClientThrowsException() {
        when(employeeFeignClient.getAllEmployees())
                .thenThrow(new FeignException.NotFound("", request, null, new HashMap<>()));
        APIException apiException = assertThrows(APIException.class, () -> iEmployeeService.getTopTenHighestEarningEmployeeNames());
        assertEquals(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, apiException.getErrorCode());
    }

    @Test
    void testCreateEmployee() {
        when(employeeFeignClient.createEmployee(any())).thenReturn(getEmployeeResponse());
        Employee employee = iEmployeeService.createEmployee(getEmployeeInput());
        assertEquals(getEmployee(), employee);
    }


}