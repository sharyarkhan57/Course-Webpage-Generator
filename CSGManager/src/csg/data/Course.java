package csg.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sharyar khan
 */
public class Course {
    
    private final StringProperty subject;
    private final StringProperty number;
    private final StringProperty semester;
    private final StringProperty year;
    private final StringProperty title;
    private final StringProperty instructorName;
    private final StringProperty instructorHome;
    private final StringProperty exportDirectory;
    private final StringProperty bannerSchoolImage;
    private final StringProperty leftFooterImage;
    private final StringProperty rightFooterImage;


     public Course(){
         
        this.subject= new SimpleStringProperty("");
        this.number= new SimpleStringProperty("");
        this.semester= new SimpleStringProperty("");
        this.year= new SimpleStringProperty("");
        this.title= new SimpleStringProperty("");
        this.instructorName= new SimpleStringProperty("");
        this.instructorHome= new SimpleStringProperty("");
        this.exportDirectory= new SimpleStringProperty("");
        this.bannerSchoolImage = new SimpleStringProperty("");
        this.leftFooterImage= new SimpleStringProperty("");
        this.rightFooterImage= new SimpleStringProperty("");
        
     }

    public Course(String subject, String number, String semester, String year, String title,String instructorName, String instructorHome,
            String exportDirectory, String bannerSchool, String leftFooter, String rightFooter) {
       
        this.subject = new SimpleStringProperty(subject);
        this.number = new SimpleStringProperty(number);
        this.semester = new SimpleStringProperty(semester);
        this.year =  new SimpleStringProperty(year);
        this.title = new SimpleStringProperty(title);
        this.instructorName = new SimpleStringProperty(instructorName);
        this.instructorHome = new SimpleStringProperty(instructorHome);
        this.exportDirectory = new SimpleStringProperty(exportDirectory);
        this.bannerSchoolImage= new SimpleStringProperty(bannerSchool);
        this.leftFooterImage= new SimpleStringProperty(leftFooter);
        this.rightFooterImage= new SimpleStringProperty(leftFooter);

    }

    public String getSubject() {
        return subject.get();
    }

    public String getNumber() {
        return number.get();
    }

    public String getSemester() {
        return semester.get();
    }

    public String getYear() {
        return year.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getInstructorName() {
        return instructorName.get();
    }

    public String getInstructorHome() {
        return instructorHome.get();
    }

    public String getExportDirectory() {
        return exportDirectory.get();
    }
    
    public StringProperty getBannerSchoolImage() {
        return bannerSchoolImage;
    }

    public StringProperty getLeftFooterImage() {
        return leftFooterImage;
    }

    public StringProperty getRightFooterImage() {
        return rightFooterImage;
    }

    
    public void setSubject(String newSubject) {
        subject.set(newSubject);
    }

    public void setNumber(String newNumber) {
        number.set(newNumber);
    }

    public void setSemester(String newSemester) {
        semester.set(newSemester);
    }

    public void setYear(String newYear) {
        year.set(newYear);
    }

    public void setTitle(String newTitle) {
        title.set(newTitle);
    }

    public void setInstructorName(String newInstructorName) {
        instructorName.set(newInstructorName);
    }

    public void setInstructorHome(String newInstructorHome) {
        instructorName.set(newInstructorHome);
    }

    public void setExportDirectory(String newExportDirectory) {
        exportDirectory.set(newExportDirectory);
    }
    
       
    public void setBannerSchoolImage(String newBannerSchoolImage) {
        bannerSchoolImage.set(newBannerSchoolImage);
    }

    public void setLeftFooterImage(String newLeftFooter) {
        leftFooterImage.set(newLeftFooter);
    }
    
    public void setRightFooterImage(String newRightFooter) {
        rightFooterImage.set(newRightFooter);
    }
    
    
}