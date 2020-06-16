package Lab.Serialization;

import Lab.Objects.Album;
import Lab.Objects.Coordinates;
import Lab.Objects.MusicBand;
import Lab.Objects.MusicGenre;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс десериализатора MusicBand из файла json
 */
public class MusicBandDeserializer implements JsonDeserializer<MusicBand> {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private Gson gson = new Gson();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Override
    public MusicBand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Integer id;
        String name;
        JsonObject jsonObject= jsonElement.getAsJsonObject();
        JsonElement jsonId=jsonObject.get("id");
        if(jsonId==JsonNull.INSTANCE)
            id=null;
        else
            id=jsonId.getAsInt();
        JsonElement jsonName=jsonObject.get("name");
        if(jsonName==JsonNull.INSTANCE){
            name=null;
        }
        else
            name=jsonName.getAsString();
        Coordinates coord;
        JsonElement jsonCoordinates = jsonObject.get("coordinates");
        coord = gson.fromJson(jsonCoordinates, Coordinates.class);
        JsonElement jsonCreationDate=jsonObject.get("creationDate");
        JsonElement jsonNumberOfParticipants=jsonObject.get("numberOfParticipants");
        Integer numberOfParticipants;
        if(jsonNumberOfParticipants==JsonNull.INSTANCE)
            numberOfParticipants=null;
        else
            numberOfParticipants=jsonNumberOfParticipants.getAsInt();
        JsonElement jsonAlbumsCount=jsonObject.get("albumsCount");
        JsonElement jsonEstablishmentDate=jsonObject.get("establishmentDate");
        JsonElement jsonGenre=jsonObject.get("genre");
        MusicGenre mg;
        try{
            mg=MusicGenre.valueOf(jsonGenre.getAsString());
        }
        catch (IllegalArgumentException e){
            mg=null;
        }
        Album alb;
        JsonElement jsonBestAlbum = jsonObject.get("bestAlbum");
        alb = gson.fromJson(jsonBestAlbum, Album.class);
        java.util.Date date;
        java.time.LocalDate localDate;
        if(jsonEstablishmentDate!=JsonNull.INSTANCE) {
            try {
                date = sdf.parse(jsonEstablishmentDate.getAsString());

            } catch (ParseException e) {
                throw new JsonSyntaxException("");
            }
        }
        else
            date=null;
        try{
            localDate = LocalDate.parse(jsonCreationDate.getAsString(), dateTimeFormatter);
        }
        catch (java.time.format.DateTimeParseException e) {
            localDate=null;
        }
        MusicBand mb = new MusicBand(id,name, coord,
                localDate, numberOfParticipants, jsonAlbumsCount.getAsLong(),
                date, mg , alb);
        return mb;
    }
}
