package com.note_awesome.models;

import com.note_awesome.NoteAwesomeEnv;

import java.net.URI;
import java.net.URL;

public enum NoteBackgroundImage {
    GROCERY_LIGHT_IMAGE("grocery_light_0609.png"),
    FOOD_LIGHT_IMAGE("food_light_0609.png"),
    MUSIC_LIGHT_IMAGE("music_light_0609.png"),
    RECIPE_LIGHT_IMAGE("recipe_light_0609.png"),
    NOTES_LIGHT_IMAGE("notes_light_0609.png"),
    PLACES_LIGHT_IMAGE("places_light_0609.png"),
    TRAVEL_LIGHT_IMAGE("travel_light_0614.png"),
    VIDEO_LIGHT_IMAGE("video_light_0609.png"),
    CELEBRATION_LIGHT_IMAGE("celebration_light_0714.png");


    private final String imageName;

    NoteBackgroundImage(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public URL getImageUrl() {
        return NoteAwesomeEnv.class.getResource("images/" + imageName);
    }

}
