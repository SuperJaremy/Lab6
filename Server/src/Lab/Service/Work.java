package Lab.Service;

import Lab.Commands.Command;
import Lab.Commands.*;
import Lab.Objects.MusicBand;
import Lab.Serialization.MusicBandDeserializer;
import Lab.Serialization.MusicBandSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class Work {
    private static final  Map<String, Command> Commands = new HashMap<>();
    Vector<MusicBand>V;
    private final Queue<Command> History = new LinkedList<>();
    private final Date creationDate;
    private Date changeDate;
    public static Map<String,Command> getCommands(){
        return Commands;
    }
    static Answer falseAnswer= new Answer("Такой команды не существует",
            false,false);
    Path path;
    Work(Path path) throws Exception {
        this.path=path;
        if (FileTester.TestFileToRead(this.path)) {
            Gson gson = new GsonBuilder().
                    registerTypeAdapter(MusicBand.class, new MusicBandDeserializer()).create();
            try (BufferedReader reader = new BufferedReader(new
                    FileReader(this.path.toFile()))) {
                BasicFileAttributes bfa = Files.readAttributes(this.path,
                        BasicFileAttributes.class);
                creationDate=new Date(bfa.creationTime().toMillis());
                changeDate = new Date(bfa.lastModifiedTime().toMillis());
                Type vectorType = new TypeToken<Vector<MusicBand>>() {
                }.getType();
                V = gson.fromJson(reader, vectorType);
            }
            catch (JsonSyntaxException e){
                System.out.println("Ошибка в файле");
                throw new NullPointerException();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                throw new NullPointerException();
            }
        }
        else
            throw new Exception();
    }
    static {
        Command info = new Info();
        Command help = new Help();
        Command show = new Show();
        Command add = new Add();
        Command clear = new Clear();
        Command exit = new Exit();
        Command remove=new RemoveFirst();
        Command ifMax = new AddIfMax();
        Command history = new History();
        Command sum = new SumOfNumberOfParticipants();
        Command count=new CountByNumberOfParticipants();
        Command printFieldDescendingNumberOfParticipants=
                new PrintFieldDescendingNumberOfParticipants();
        Command uid=new Update();
        Command remove_id = new RemoveByID();
        Command script = new ExecuteScript();
        Commands.put(info.getName(), info);
        Commands.put(help.getName(), help);
        Commands.put(show.getName(),show);
        Commands.put(add.getName(), add);
        Commands.put(clear.getName(),clear);
        Commands.put(exit.getName(), exit);
        Commands.put(remove.getName(),remove);
        Commands.put(ifMax.getName(),ifMax);
        Commands.put(history.getName(),history);
        Commands.put(sum.getName(),sum);
        Commands.put(count.getName(),count);
        Commands.put(printFieldDescendingNumberOfParticipants.getName(),
        printFieldDescendingNumberOfParticipants);
        Commands.put(uid.getName(),uid);
        Commands.put(remove_id.getName(),remove_id);
        Commands.put(script.getName(),script);
    }
    public Answer execute(Meta meta) {
        Answer answer;
        String command = meta.getName();
        if(Commands.containsKey(command)) {
            answer = Commands.get(command).act(meta, this);
            if (answer.isSuccess()) {
                if (History.size() == 8) {
                    History.poll();
                }
                History.offer(Commands.get(command));
            }
            if (Commands.get(command).getRewrite()) {
                if (FileTester.TestFileToWrite(path)) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls()
                            .registerTypeAdapter(MusicBand.class, new MusicBandSerializer())
                            .create();
                    try (BufferedWriter writer = new BufferedWriter(new
                            FileWriter(path.toFile()))) {
                        gson.toJson(V, writer);
                        writer.flush();
                        changeDate= new Date();
                    } catch (IOException e) {
                        throw new NullPointerException();
                    }
                }
                else throw new NullPointerException();
            }
            return answer;
        }
        return falseAnswer;
    }

    public Queue<Command> getHistory() {
        return History;
    }

    public Vector<MusicBand> getV() {
        return V;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }
}