package org.example.ejemplotablasagregarquitarfilas;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * es una clase que representa una persona, con un id, primer apellido
 * segundo apellido y fecha de nacimiento. estos datos son usados para
 * rellenar datos de una tabla que funcionacomo pantalla enm otra clase.
 * Ademas tiene un valor estatico que es el numero de personas creadas
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(0);
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    // An enum for age categories
    /**
     * enumera las categorias para las edades de las Person
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };

    /**
     * constructor tipico
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * contructor que recibe valores para sus apellidos y fecha de nacimiento
     * @param firstName
     * primer apellido
     * @param lastName
     * segundo apellido
     * @param birthDate
     * fecha de nacimiento
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    /**
     * permite obtener el ID de la Persona
     * @return
     * el PersonID
     */
    public int getPersonId() {
        return personId;
    }
    /**
     * permite setear un ID de persona desde una clase ajena
     * @param personId
     * IDentificador de la persona
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    /**
     * obtiene el primer apellido de una persona
     * @return
     * el primer apellido
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * permite setear el primer apellido de una persona desde una clase ajena
     * @param firstName
     * primer apellido de una persona
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * obtiene el segundo apellido de una persona
     * @return
     * el segundo apellido
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * setea el segundo apellido de una persona desde una clase ajena
     * @param lastName
     * segundo apellido de una persona
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * obtiene la fecha de nacimiento de una persona
     * @return
     * la fecha de nacimiento
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }
    /**
     * setea la fecha de nacimiento de una persona desde una clase ajena
     * @param birthDate
     * la fecha de nacimiento de una persona
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /* Domain specific business rules */
    /**
     * valida si la fecha de nacimiento esta corrctamente hecha
     * @param bdate
     * fecha de nacimiento
     * @return
     * true si es correcta, false si esta mal hecha
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /* Domain specific business rules */
    /**
     * misma operacion que el anterior metodo, pero esta vez le pasa como argumento una lista de errores
     * @param bdate
     * fecha de nacimiento
     * @param errorList
     * lista de errores
     * @return
     * true si es correcta, false si esta mal hecha
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        // Birth date cannot be in the future
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /* Domain specific business rules */
    /**
     * valida si la persona esta bien realizada
     * @param errorList
     * lista de errores
     * @return
     * true si la persona esta bien realizada, false si tiene algun error de monenclatura
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /* Domain specific business rules */
    /**
     * lo mismo que el anterior pero la persona se pasa como agrumento
     * @param p
     * persona
     * @param errorList
     * lista de errores
     * @return
     * true si la persona esta bien realizada, false si no
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().length() == 0) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /* Domain specific business rules */
    /**
     * asigna una categoria a cada persona dependiendo de su edad actual
     * @return
     * la categoria correspondiente
     */
    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    /* Domain specific business rules */
    /**
     * guarda la persona dentro de la lista
     * @param errorList
     * lista de errores
     * @return
     * si la persona consiguio guardarse o no
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    @Override
    /**
     * metodo tooString para mostrar una persona y sus datos
     */
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}