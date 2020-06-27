package com.derekpark.balance.controller;

import java.time.LocalDateTime;
import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import com.derekpark.balance.service.DistributeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DistributeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "distributeService")
    private DistributeService distributeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Distribute distribute;


    @Before
    public void setUp() {

        distribute = new Distribute();
        distribute.setId(1);
        distribute.setAmount(1000);
        distribute.setRoomId("secret_room");
        distribute.setUserId(948824);
        distribute.setRegDate(LocalDateTime.now());

        Recipient recipient1 = new Recipient();
        recipient1.setAmount(200);
        recipient1.setUserId(1);
        distribute.addRecipients(recipient1);

        Recipient recipient2 = new Recipient();
        recipient2.setAmount(800);
        distribute.addRecipients(recipient2);

    }


    @Test
    public void createDistribute_test() throws Exception {

        DistributeDTO distributeDTO = new DistributeDTO();
        distributeDTO.setUserCount(3);
        distributeDTO.setAmount(12000);

        String content = objectMapper.writeValueAsString(distributeDTO);

        Mockito.when(distributeService
                .create(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(distribute);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/pay/distribute").content(content)
                .contentType(MediaType.APPLICATION_JSON).header("X-ROOM-ID", "secret_room")
                .header("X-USER-ID", 948824)).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void getDistribute_test() throws Exception {
        Mockito.when(
                distributeService.getDistribute(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(distribute);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/pay/distribute/asd")
                .header("X-ROOM-ID", "secret_room").header("X-USER-ID", 948824))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void putDistribute_test() throws Exception {

        Mockito.when(distributeService
                .updateDistribute(ArgumentMatchers.any(), ArgumentMatchers.any(),
                        ArgumentMatchers.any())).thenReturn(12000);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/pay/distribute/asd")
                .header("X-ROOM-ID", "secret_room").header("X-USER-ID", 948824))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}