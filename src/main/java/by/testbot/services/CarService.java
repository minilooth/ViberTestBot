package by.testbot.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.testbot.models.Car;
import by.testbot.repositories.CarRepository;
import lombok.SneakyThrows;

@Service
public class CarService {
    private final static Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository carRepository;

    private String CAR_PAGE = "https://bidfax.info/";

    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void update(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void delete(Car car) {
        carRepository.delete(car);
    }

    @Transactional
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Transactional
    public List<String> getBrands() {
        return carRepository.findDistinctBrand();
    }

    @Transactional
    public List<String> getModelsByBrand(String brand) {
        return carRepository.findModelByBrand(brand);
    }

    public String generateBrandString() {
        List<String> brands = getBrands();
        StringBuilder stringBuilder = new StringBuilder();

        for(String brand : brands) {
            stringBuilder.append("\n").append(brand);
        }

        return stringBuilder.toString();
    }

    public String generateModelsString(String brand) {
        List<String> models = getModelsByBrand(brand);
        StringBuilder stringBuilder = new StringBuilder();

        System.out.println(models);

        for(String model : models) {
            stringBuilder.append("\n").append(model);
        }

        return stringBuilder.toString();
    }

    public String generateLink(String brand, String model, Integer yearFrom, Integer yearTo) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(CAR_PAGE)
                     .append(brand.replace(' ', '-').toLowerCase())
                     .append("/")
                     .append(model.replace(' ', '-').toLowerCase())
                     .append("/f/")
                     .append("from-year=")
                     .append(yearFrom)
                     .append("/to-year=")
                     .append(yearTo)
                     .append("/");

        return stringBuilder.toString();
    }

    // @SneakyThrows
    // public void parseCars() {


    //     WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //     webClient.getOptions().setThrowExceptionOnScriptError(false);
    //     webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    //     webClient.setCssErrorHandler(new SilentCssErrorHandler());
    //     webClient.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
    //     webClient.setIncorrectnessListener(new IncorrectnessListener() {
    //         @Override
    //         public void notify(String arg0, Object arg1) { }
    //     });

    //     HtmlPage page = null;

    //     // try {
    //         logger.info("Trying to parse " + CAR_PAGE);
    //         page = webClient.getPage(CAR_PAGE);
    //     // }
    //     // catch (IOException ex) {
    //         // System.out.println(ex.getMessage());
    //     // }

    //     List<?> htmlBrands = page.getByXPath("//html/body/div[1]/main/section[1]/div/div/div[2]/div/div[1]/div[1]/ul/div/a");
    //     List<String> brands = new ArrayList<>();

    //     for(Object htmlBrand : htmlBrands) {
    //         brands.add(((HtmlAnchor)htmlBrand).getTextContent());
    //     }

    //     webClient.close();

    //     for(String brand : brands) {
    //         if (brand.equals("Fiat") || brand.equals("Mclaren automotive")) {
    //         HtmlPage brandPage = webClient.getPage(CAR_PAGE + brand.replace(' ', '-').toLowerCase() + "/");

    //         List<?> htmlModels = brandPage.getByXPath("//html/body/div[1]/main/section/div/div/div[2]/div/div[1]/div[2]/ul/div/ul/a");

    //         for(Object htmlModel : htmlModels) {
    //             Car car = Car.builder()
    //                         .brand(brand)
    //                         .model(((HtmlAnchor)htmlModel).getTextContent())
    //                         .build();

    //             this.save(car);
    //         }

    //         webClient.close();
    //         Thread.sleep(10000);
    //         }
    //     }

    //     logger.info("Successfully parsed");
    // }
}
