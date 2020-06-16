package Lab.Serialization;

import Lab.Objects.MusicBand;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Класс сериализатора класса MusicBand в json
 */
public class MusicBandSerializer implements JsonSerializer<MusicBand> {
    private Gson gson = new Gson();
    private SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Override
    public JsonElement serialize(MusicBand musicBand, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject musicBandJsonObj = new JsonObject();
        musicBandJsonObj.addProperty("id", musicBand.getId());
        musicBandJsonObj.addProperty("name", musicBand.getName());
        musicBandJsonObj.add("coordinates",gson.toJsonTree(musicBand.getCoordinates()));
        musicBandJsonObj.addProperty("creationDate",formatter.format(musicBand.getCreationDate()));
        musicBandJsonObj.addProperty("numberOfParticipants",musicBand.getNumberOfParticipants());
        musicBandJsonObj.addProperty("albumsCount",musicBand.getAlbumsCount());
        if(musicBand.getEstablishmentDate()!=null)
            musicBandJsonObj.addProperty("establishmentDate",sdf.format(musicBand.getEstablishmentDate()));
        else
            musicBandJsonObj.add("establishmentDate", JsonNull.INSTANCE);
        musicBandJsonObj.addProperty("genre",musicBand.getGenre().toString());
        musicBandJsonObj.add("bestAlbum",gson.toJsonTree(musicBand.getBestAlbum()));
        return  musicBandJsonObj;
    }
}
