//package com.cheese.web;
//
//import com.cheese.database.config.DatabaseConfig;
//import com.cheese.web.config.WebMvcConfig;
//import com.cheese.model.dto.UserRegisterDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Paths;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringJUnitWebConfig(classes = {WebMvcConfig.class, DatabaseConfig.class})
//public class UserControllerTest {
//
//    static UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
//    static MockMultipartFile avatar;
//    static InputStream imageContent;
//
//    MockMvc mockMvc;
//    ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
//        this.objectMapper = new ObjectMapper();
//        System.out.println("init...");
//        try {
//            imageContent = new FileInputStream(Paths.get("/home/xxxcrel/Pictures/test.png").toFile());
//            avatar = new MockMultipartFile("avatar", "test.png", MediaType.IMAGE_PNG_VALUE, imageContent);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    @Rollback(false)
//    public void testRegister() throws Exception {
//
////        this.mockMvc.perform(post("/register")
////                .content(objectMapper.writeValueAsString(userRegisterDTO))
////                .contentType(MediaType.MULTIPART_FORM_DATA)
////                .accept(MediaType.ALL))
////                .andExpect(status().isCreated());
//        this.mockMvc.perform(multipart("/register")
//                .file(avatar)
//                .param("username", "xxxcrel")
//                .param("password", "xc514xxx")
//                .param("nickname", "克里尔")
//                .param("email", "123321@qq.com")
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .accept(MediaType.ALL))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @Transactional
//    public void testGetCurrentUser() throws Exception {
//
//        this.mockMvc.perform(get("/user").with(httpBasic("creldevi", "helloxc"))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.username").value("creldevi"));
//    }
//
////    @Test
////    public void testUpdateAvatar() {
////        this.mockMvc.perform(multipart("/user/profile")
////                .file(uploadAvatar)
////                .with(httpBasic("xxxcrel", "xc514xxx")))
////                .andExpect(status().isOk());
////    }
//
//
//}
