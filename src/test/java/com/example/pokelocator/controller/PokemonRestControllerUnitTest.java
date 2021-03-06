package com.example.pokelocator.controller;

import com.example.pokelocator.model.LetsGo;
import com.example.pokelocator.model.UltraSunMoon;
import com.example.pokelocator.service.LetsGoService;
import com.example.pokelocator.service.UltraSunAndMoonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PokemonRestController.class)
class PokemonRestControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LetsGoService letsGoService;

    @MockBean
    private UltraSunAndMoonService ultraSunAndMoonService;

    //mocked Ultra Sun/Moon pokemon object
    UltraSunMoon pokemon = new UltraSunMoon("617b4d171dab5366a1d6dcdd", 1, "Bulbasaur",
            "Melemele Island: Use the QR Scanner's Island Scan on Melemele Island on a Friday. Bulbasaur can then be encountered in Route 2.",
            "https://www.serebii.net/swordshield/pokemon/001.png");

    //mocked LetsGo pokemon object
    LetsGo letsGoPokemon = new LetsGo("61796c63668c1db3a53ed8e1", 3, "Venusaur",
            "Raise Ivysaur to level 32.", "https://www.serebii.net/swordshield/pokemon/002.png");

    @Test
    void shouldGetSunAndMoonPokemonById() throws Exception {

       //mock the service call for a pokemon with a pokeId of 1. Returns the mocked object.
       Mockito.when(ultraSunAndMoonService.getPokemonByPokeId(1)).thenReturn(pokemon);

       // URl to test against with the request param of pokeId 1
       String url = "/api/pokemon/ultra-sun-and-moon?pokeId=1";

       //result of the mock api call. Should return status 200.
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

       //gets result of call, and turns into string.
       String actualJsonResponse = mvcResult.getResponse().getContentAsString();

       //writes the mock pokemon object as a string.
       String expectedJsonResponse = objectMapper.writeValueAsString(pokemon);

       //compares the mock call result to the mock pokemon object.
       assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    void shouldGetSunAndMoonPokemonByName() throws Exception {
        Mockito.when(ultraSunAndMoonService.getPokemonByPokeName("Bulbasaur")).thenReturn(pokemon);
        String url = "/api/pokemon/ultra-sun-and-moon?pokeName=Bulbasaur";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(pokemon);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);

    }

    @Test
    void shouldGetLetsGoPokemonById() throws Exception {
        Mockito.when(letsGoService.getPokemonByPokeId(3)).thenReturn(letsGoPokemon);
        String url = "/api/pokemon/lets-go?pokeId=3";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(letsGoPokemon);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    void shouldGetLetsGoPokemonByName() throws Exception {
        Mockito.when(letsGoService.getPokemonByPokeName("Venusaur")).thenReturn(letsGoPokemon);
        String url = "/api/pokemon/lets-go?pokeName=Venusaur";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(letsGoPokemon);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

}