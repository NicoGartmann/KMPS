import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Funktional {
    
    public ArrayList<String> createTokenList(byte[] file_contents, int start_character , ArrayList<String> tokenList) {
        if(start_character == file_contents.length) {
            return tokenList; 
        } 
        if(file_contents[start_character] == '\n' || file_contents[start_character] == '\r' || file_contents[start_character] == '\t') {
            createTokenList(file_contents, start_character+1, tokenList); 
        } else if(new String(file_contents, start_character, 7, StandardCharsets.UTF_8).equals(new String("<album>"))) {
            tokenList.add("album"); 
            createTokenList(file_contents,start_character+7, tokenList); 

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("</album>"))) {
            tokenList.add("/album"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        }  else if(new String(file_contents, start_character, 7, StandardCharsets.UTF_8).equals(new String("<title>"))) {
            tokenList.add("title"); 
            createTokenList(file_contents, start_character+7, tokenList);

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("</title>"))) {
            tokenList.add("/title"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("<artist>"))) {
            tokenList.add("artist"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        } else if(new String(file_contents, start_character, 9, StandardCharsets.UTF_8).equals(new String("</artist>"))) {
            tokenList.add("/artist"); 
            createTokenList(file_contents, start_character+9, tokenList); 

        } else if(new String(file_contents, start_character, 6, StandardCharsets.UTF_8).equals(new String("<date>"))) {
            tokenList.add("date"); 
            createTokenList(file_contents, start_character+6, tokenList); 

        } else if(new String(file_contents, start_character, 7, StandardCharsets.UTF_8).equals(new String("</date>"))) {
            tokenList.add("/date"); 
            createTokenList(file_contents, start_character+7, tokenList); 

        } else if(new String(file_contents, start_character, 7, StandardCharsets.UTF_8).equals(new String("<track>"))) {
            tokenList.add("track"); 
            createTokenList(file_contents, start_character+7, tokenList);

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("</track>"))) {
            tokenList.add("/track"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("<length>"))) {
            tokenList.add("length"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        } else if(new String(file_contents, start_character, 9, StandardCharsets.UTF_8).equals(new String("</length>"))) {
            tokenList.add("/lenght"); 
            createTokenList(file_contents, start_character+9, tokenList); 

        } else if(new String(file_contents, start_character, 9, StandardCharsets.UTF_8).equals(new String("<writing>"))) {
            tokenList.add("writing"); 
            createTokenList(file_contents, start_character+9, tokenList); 

        } else if(new String(file_contents, start_character, 10, StandardCharsets.UTF_8).equals(new String("</writing>"))) {
            tokenList.add("/writing"); 
            createTokenList(file_contents, start_character+10, tokenList);

        } else if(new String(file_contents, start_character, 8, StandardCharsets.UTF_8).equals(new String("<rating>"))) {
            tokenList.add("rating"); 
            createTokenList(file_contents, start_character+8, tokenList); 

        } else if(new String(file_contents, start_character, 9, StandardCharsets.UTF_8).equals(new String("</rating>"))) {
            tokenList.add("/rating"); 
            createTokenList(file_contents, start_character+9, tokenList); 
        } else if(new String(file_contents, start_character, 9, StandardCharsets.UTF_8).equals(new String("<feature>"))) {
            tokenList.add("feature"); 
            createTokenList(file_contents, start_character+9, tokenList);  
        } else if(new String(file_contents, start_character, 10, StandardCharsets.UTF_8).equals(new String("</feature>"))) {
            tokenList.add("/rating"); 
            createTokenList(file_contents, start_character+10, tokenList); 
        } else {
                String tmp = new String();
                int wortLength = getLength(file_contents, start_character, 0);
                tmp = new String(file_contents, start_character, wortLength, StandardCharsets.UTF_8);
                tokenList.add(tmp);
                createTokenList(file_contents,start_character+wortLength,tokenList);
        }

        return tokenList; 
    }

    public ArrayList<Album> parseFile(ArrayList<String> tokenList, int index,ArrayList<Album> alben ,Album album, Track track, boolean alb, boolean tra) {
        if(index < tokenList.size()) {
            if(tokenList.get(index) == "album") {
                parseFile(tokenList, index+1, alben, album, track, true, tra); 
            } else if (tokenList.get(index) == "/album") {
                alben.add(album); 
                parseFile(tokenList, index+1, alben, new Album(), track, false, tra);
            } else if (tokenList.get(index) == "track") {
                parseFile(tokenList, index+1, alben, album, track, alb, true); 
            } else if(tokenList.get(index) == "/track") { 
                album.tracks.add(track); 
                parseFile(tokenList, index+1, alben, album, new Track(), alb, false); 
            } else if(tokenList.get(index) == "writing") {
                track.writers.add(tokenList.get(index+1)); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "feature") {
                track.features.add(tokenList.get(index+1)); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "title" && tra == true) {
                track.title = tokenList.get(index+1); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "length") {
                track.length = tokenList.get(index+1); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "rating") {
                track.rating = Integer.parseInt(tokenList.get(index+1)); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "artist") {
                album.artist = tokenList.get(index+1); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "title" && tra == false) {
                album.title = tokenList.get(index+1); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else if(tokenList.get(index) == "date") {
                album.date = tokenList.get(index+1); 
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            } else {
                parseFile(tokenList, index+1, alben, album, track, alb, tra); 
            }
        }
        return alben; 
    }

    public static int getLength(byte[] xml, int pos, int length) {
        if(xml[pos + length] != '<') {
            length++;
            return getLength(xml, pos, length);
        }else{
            return length;
        }
    }
    
    public static void main(String[] args) throws IOException {
        byte[] file_contents = Files.readAllBytes(Paths.get("alben.xml"));
        Funktional tmp = new Funktional(); 
        ArrayList<String> tokenList = new ArrayList<String>(); 
        ArrayList<Album> alben = new ArrayList<Album>(); 
        alben = tmp.parseFile(tmp.createTokenList(file_contents, 0, tokenList),0, alben, new Album(), new Track(), false, false);
        System.out.println(alben); 
        System.out.println(tokenList); 

    }    
}
