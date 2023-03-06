package steps;

import adapters.MilestoneAdapter;
import baseEntities.BaseStep;
import io.restassured.response.Response;
import models.Milestone;
import org.apache.http.HttpStatus;
import org.openqa.selenium.WebDriver;
import pages.AddMilestonePage;
import pages.MilestonesPage;
import tests.ui.positive.MilestoneTests;
import utils.Endpoints;

import java.io.File;

import static io.restassured.RestAssured.given;

public class MilestoneStep extends BaseStep {
    private AddMilestonePage addMilestonePage;
    private int milestoneId;
    private Response response;

    public MilestoneStep(WebDriver driver) {
        super(driver);
        addMilestonePage = new AddMilestonePage(driver);
    }

    public MilestoneStep() {

    }
//    public void addMilestone(String name, String description) {
//        addMilestonePage.getNameMilestoneInput().sendKeys(name);
//        addMilestonePage.getDescriptionMilestoneInput().sendKeys(description);
//        addMilestonePage.getAddMilestoneButton().click();
//    }

    public void uploadFile(String pathToFile) {
        addMilestonePage.getAddImage().click();
        addMilestonePage.getUploadFile().sendKeys(pathToFile);
        addMilestonePage.getAttachButton().click();

    }

    public void dialogWindows() {
        addMilestonePage.getDialogWindow().click();
        addMilestonePage.getAddTableDialogWindow().click();
    }

    public AddMilestonePage uploadFileSuccessful(String pathToFile) {
        uploadFile(pathToFile);
        return new AddMilestonePage(driver);
    }
//    public MilestonesPage moveToMilestonesPageSuccessful(Milestone milestone) {
//        addMilestone(milestone.getName(), milestone.getDescription());
//        return new MilestonesPage(driver);
//    }

    public AddMilestonePage checkDialogWindow() {
        dialogWindows();
        return new AddMilestonePage(driver);
    }

//    milestoneId = response.getBody().jsonPath().getInt("id");


    // API private
    public Response add(int projectId, File file) {
        return given()
                .pathParams("project_id", projectId)
                .body(file)
                .when()
                .post(Endpoints.ADD_MILESTONE)
                .then()
                .log().body()
                .extract().response();

    }

    public Response get(int milestoneId) {
        return given()
                .pathParams("milestone_id", milestoneId)
                .when()
                .get(Endpoints.GET_MILESTONE)
                .then()
                .log().body()
                .extract().response();
    }

    public Response update(int milestoneId, File file) {
        return given()
                .pathParams("milestone_id", milestoneId)
                .body(file)
                .when()
                .post(Endpoints.UPDATE_MILESTONE)
                .then()
                .log().body()
                .extract().response();
    }

    public Response delete(int milestoneId) {
        return given()
                .pathParams("milestone_id", milestoneId)
                .when()
                .post(Endpoints.DELETE_MILESTONE)
                .then()
                .log().body()
                .extract().response();
    }

    //API public

    public Response addApiMilestone(int projectId, File file) {
        response = add(projectId, file);
        return response;
    }

    public Response getApiMilestone(int milestoneId) {
        return get(milestoneId);
    }

    public Response updateApiMilestone(int milestoneId, File file) {
        return update(milestoneId, file);
    }

    public Response deleteApiMilestone(int projectId) {
        return delete(projectId);
    }

    public void setMilestoneId() {
        milestoneId = response.getBody().jsonPath().getInt("id");
    }

    public int getMilestoneId() {
        return milestoneId;
    }
}
