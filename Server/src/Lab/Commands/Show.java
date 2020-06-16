package Lab.Commands;

import Lab.Objects.MusicBand;
import Lab.Service.Answer;
import Lab.Service.Work;

public class Show  extends Command{
    {
        name="show";
        description="вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    @Override
    public String describe() {
        return name.concat(": ").concat(description);
    }

    @Override
    public Answer act(Meta meta, Work work) {
        String info="";
        for(MusicBand i : work.getV()){
            info=info.concat(i.toString()).concat("\n");
        }
        info=info.concat("Конец списка");
        return new Answer(info,true,exit);
    }
}
