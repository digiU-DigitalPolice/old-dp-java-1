package ua.in.zloch.controller;

import ua.in.zloch.CityPoliceApplication;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.in.zloch.dto.RegionListDTO;
import ua.in.zloch.service.RegionService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CityPoliceApplication.class)
@WebAppConfiguration
public class RegionControllerDocumentation {

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Mock
    private RegionService regionService;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private RegionController regionController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(regionController)
                .apply(documentationConfiguration(this.restDocumentation).uris()
                        .withScheme("http")
                        .withHost("162.211.230.155")
                        .withPort(8080))
                .build();
    }

    @Test
    public void testGetRegions() throws Exception {
        when(conversionService.convert(any(List.class), any(Class.class))).thenReturn(createRegionDTO());

        mockMvc.perform(get("/regions")
                .accept(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .param("regionIds", "4610136300,4610136600")
                .param("name", "ЗАЛІЗНИЧНИЙ")
                .param("koatuu", "4610136300"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedUnfilteredRegionsListJSON(), true))
                .andDo(document("get-region",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("id").attributes().description("id регіону"),
                                parameterWithName("regionIds").attributes().description("список koatuu id регіонів, записані через кому"),
                                parameterWithName("name").attributes().description("ім'я району для пошуку"),
                                parameterWithName("koatuu").attributes().description("koatuu району для пошуку")
                        )));
    }

    private String expectedUnfilteredRegionsListJSON() {
        return readFromResource("ua.in.zloch/regions-list.json");
    }

    private String readFromResource(String resourceName) {
        try {
            return new Scanner(new ClassPathResource(resourceName).getInputStream()).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RegionListDTO createRegionDTO() {
        RegionListDTO dtoList = new RegionListDTO();
        RegionListDTO.RegionDTO dto = new RegionListDTO().new RegionDTO();
        dto.setId(123l);
        dto.setName("Frankivsky");
        dto.setKoatu(12312l);
        dto.setBoundaries("24.01405,49.783669 24.018125,49.801882 24.018901,49.805269 24.01901,49.805762 24.019235,49.808163 24.019446,49.810959 24.019474,49.811333 24.019499,49.811723 24.019505,49.811811 24.019652,49.814277 24.019753,49.814674 24.020118,49.818499 24.020226,49.819868 24.020738,49.824769 24.02094,49.825192 24.020398,49.825341 24.019608,49.825731 24.017947,49.826593 24.018881,49.827531 24.019088,49.827768 24.019141,49.82788 24.019326,49.827884 24.019638,49.827881 24.019985,49.828242 24.020131,49.829871 24.019887,49.830461 24.020687,49.831096 24.020756,49.831177 24.018426,49.832181 24.018383,49.832281 24.018378,49.83235 24.018388,49.832555 24.018522,49.832686 24.018802,49.833194 24.019124,49.83368 24.019278,49.83382 24.019543,49.834026 24.020053,49.834387 24.020227,49.834573 24.019608,49.834603 24.018924,49.834645 24.017416,49.834648 24.017091,49.834653 24.016383,49.834663 24.014985,49.834669 24.014401,49.834671 24.013966,49.834672 24.012829,49.834681 24.011166,49.834705 24.009771,49.834749 24.007755,49.834801 24.007484,49.834823 24.007155,49.834849 24.005382,49.835425 24.004594,49.836019 24.003914,49.837077 24.00386,49.837059 24.00359,49.836978 24.002919,49.836776 24.002739,49.836755 24.002538,49.836731 24.001481,49.836537 24.000926,49.836435 23.997634,49.835841 23.994296,49.835256 23.99242,49.834908 23.991079,49.834677 23.990323,49.834549 23.98588,49.833729 23.983873,49.833408 23.98281,49.833241 23.979783,49.832713 23.978109,49.832402 23.977192,49.83224 23.976149,49.832069 23.975979,49.832033 23.974767,49.831834 23.97463,49.83181 23.973746,49.831673 23.973483,49.83163 23.973383,49.831615 23.973023,49.83156 23.972769,49.831521 23.972622,49.831499 23.972621,49.831371 23.972693,49.830152 23.972694,49.830151 23.972693,49.830151 23.972923,49.82936 23.973259,49.829391 23.973778,49.828877 23.974279,49.828345 23.974461,49.828196 23.974497,49.828082 23.974797,49.827944 23.975153,49.82774 23.975576,49.827587 23.975976,49.827453 23.976469,49.827337 23.977235,49.827157 23.980639,49.826129 23.980759,49.826091 23.983633,49.825137 23.984622,49.824845 23.987117,49.82407 23.987352,49.823997 23.987329,49.823944 23.987352,49.823937 23.989106,49.823416 23.989226,49.823394 23.988632,49.821262 23.98834,49.820383 23.987753,49.818865 23.987684,49.818685 23.987592,49.818448 23.987514,49.818297 23.987405,49.81803 23.986909,49.817126 23.985618,49.814881 23.984542,49.81297 23.984355,49.812606 23.984102,49.812424 23.98354,49.812042 23.982987,49.811665 23.982885,49.811477 23.982325,49.809099 23.98141,49.805496 23.980991,49.803519 23.980647,49.802108 23.980441,49.801214 23.980581,49.801108 23.980522,49.801085 23.980456,49.801088 23.980456,49.80108 23.98154,49.800987 23.982116,49.800827 23.982952,49.80015 23.983319,49.800044 23.983343,49.800037 23.98366,49.799945 23.98366,49.799901 23.98446,49.799717 23.985201,49.799283 23.98579,49.798911 23.986519,49.798401 23.988036,49.798265 23.995093,49.797521 23.996386,49.7974 23.99735,49.797629 23.999173,49.797508 24.000866,49.797371 24.001172,49.797424 24.002336,49.797349 24.00263,49.797166 24.008299,49.796551 24.009663,49.796407 24.010521,49.796209 24.011441,49.795963 24.01231,49.79623 24.012477,49.795321 24.012555,49.795144 24.012377,49.794443 24.012958,49.794398 24.012941,49.794206 24.012433,49.792473 24.01243,49.792462 24.012219,49.792227 24.01224,49.790377 24.012255,49.78919 24.012474,49.786602 24.01254,49.786267 24.012526,49.785755 24.012498,49.785539 24.012387,49.785144 24.012013,49.783903 24.01405,49.783669 24.014012,49.783499 24.014052,49.783669 24.01405,49.783669 24.014012,49.783499");
        dtoList.addRegion(dto);

        RegionListDTO.RegionDTO dtoTwo = new RegionListDTO().new RegionDTO();
        dtoTwo.setId(1232);
        dtoTwo.setName("Galytsky");
        dtoTwo.setKoatu(12312l);
        dtoTwo.setBoundaries("24.01971192,49.81579902 24.023079,49.81870296 24.02827308,49.81974399 24.03530496,49.81856103 24.03928008,49.81796901 24.04356012,49.81678596 24.04998108,49.81856103 24.05334492,49.81875804 24.05554812,49.82094099 24.05444796,49.82209398 24.05417112,49.82238396 24.05354616,49.82279103 24.05328192,49.822965 24.05304504,49.82309901 24.052662,49.82326398 24.05046816,49.82414301 24.05035908,49.82422599 24.04965708,49.82462199 24.04888092,49.82502303 24.04839312,49.82515398 24.046119,49.82565402 24.04430316,49.82604399 24.04374012,49.82616099 24.04353816,49.826232 24.04337904,49.82633496 24.04329588,49.82643396 24.04313604,49.82649201 24.04289484,49.82662602 24.04075716,49.82768604 24.03821088,49.82869296 24.03780408,49.828887 24.037695,49.82894001 24.03749592,49.82901597 24.03727992,49.82904801 24.03716292,49.82904297 24.03689688,49.82898897 24.03663912,49.82897304 24.03656388,49.82898897 24.03651384,49.82905296 24.03652212,49.82912298 24.03658908,49.82926302 24.03674712,49.82949504 24.03681408,49.82951601 24.03718812,49.82953203 24.03799488,49.82949999 24.03817812,49.82952699 24.03828612,49.82959098 24.03845316,49.82973102 24.03747108,49.82995197 24.03662184,49.83014601 24.03631404,49.83022701 24.03440892,49.83065199 24.03430092,49.83069501 24.03282312,49.83102504 24.03317484,49.83144804 24.03363204,49.83198003 24.03368388,49.83207597 24.03389592,49.83265404 24.03404604,49.83320898 24.034059,49.833315 24.03416484,49.83371604 24.03419292,49.83380604 24.03431388,49.83419403 24.03447192,49.83455196 24.03468792,49.83476004 24.03492192,49.83491799 24.03498312,49.83518097 24.03500508,49.83569001 24.03499608,49.83606 24.03495216,49.836123 24.03487296,49.83617403 24.03481104,49.836231 24.034851,49.83631398 24.03492084,49.83708501 24.03500508,49.83740901 24.03512892,49.83797997 24.03517104,49.83819003 24.03530892,49.83879699 24.03543096,49.83888096 24.035571,49.83903801 24.03587592,49.83945903 24.03608796,49.83975297 24.03629496,49.83994296 24.03670284,49.84043301 24.03591012,49.84056396 24.03571896,49.84097103 24.03544212,49.84161597 24.03533592,49.84185402 24.03533592,49.84198101 24.03535212,49.84210197 24.03522,49.84263603 24.03499392,49.84328196 24.03484704,49.84347996 24.03443412,49.84396596 24.03384912,49.844025 24.03354888,49.84403499 24.033555,49.84414299 24.03357408,49.84442496 24.03350208,49.84442199 24.03318708,49.84440201 24.03202896,49.84448499 24.03131616,49.84453098 24.03028584,49.84460703 24.02998488,49.84461801 24.02953992,49.844628 24.02928288,49.84463403 24.02849808,49.84466499 24.02831484,49.84467903 24.02744616,49.84467498 24.02657208,49.84462098 24.02633196,49.84456698 24.02579808,49.84446996 24.025509,49.84436997 24.02473788,49.844196 24.024681,49.84413903 24.02463384,49.84409304 24.02308692,49.84328601 24.02283312,49.84316802 24.02208504,49.84307298 24.02035704,49.84287804 24.01869096,49.84267302 24.01879716,49.841955 24.01881804,49.84157799 24.01890984,49.84130898 24.01917696,49.84089597 24.01976088,49.84001397 24.01879104,49.83952698 24.017742,49.838967 24.01645608,49.83828597 24.01582716,49.83794802 24.01748604,49.83724296 24.01843392,49.83684597 24.01907796,49.83665598 24.01927092,49.83659703 24.019398,49.83651504 24.01996608,49.83610896 24.02048808,49.83542604 24.020991,49.83507297 24.02022708,49.83457302 24.02005284,49.83438699 24.01954308,49.834026 24.01927812,49.83381999 24.01912404,49.83368004 24.01880184,49.83319404 24.01852212,49.83268599 24.01838784,49.83255504 24.01837812,49.83235002 24.01838316,49.83228099 24.018426,49.832181 24.02075592,49.83117696 24.02068716,49.83109596 24.01988688,49.83046101 24.02013096,49.82987097 24.01998516,49.82824197 24.01963812,49.82788098 24.019326,49.82788404 24.01914096,49.82787999 24.01908804,49.82776803 24.01888104,49.82753097 24.01794684,49.82659299 24.01960788,49.82573097 24.02039808,49.825341 24.02093988,49.82519196 24.02073792,49.82476896 24.020226,49.81986801 24.01971192,49.81579902");
        dtoList.addRegion(dtoTwo);
        return dtoList;
    }
}