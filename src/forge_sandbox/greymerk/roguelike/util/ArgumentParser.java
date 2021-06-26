package forge_sandbox.greymerk.roguelike.util;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {

    List<String> args;
    
    public ArgumentParser(String[] args){
        
        this.args = new ArrayList<>();
        
        for (int i = 0; i < args.length; ++i){
            this.args.add((String)args[i]);
        }
    }
    
    public ArgumentParser(List<String> args){
        this.args = new ArrayList<>();
        this.args.addAll(args);
    }
    
    public boolean hasEntry(int index){
        return index < this.args.size();
    }
    
    public boolean match(int index, String toCompare){
        
        if(!this.hasEntry(index)) return false;
        return this.args.get(index).equals(toCompare);
    }
    
    public String get(int index){
        
        if(!this.hasEntry(index)) return null;
        return this.args.get(index); 
    }
    
    @Override
    public String toString(){
        return this.args.toString();
    }
}
