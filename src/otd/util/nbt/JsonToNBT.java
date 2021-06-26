/* 
 * Copyright (C) 2021 shadow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package otd.util.nbt;

import java.util.Stack;
import java.util.regex.Pattern;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import otd.Main;
import otd.MultiVersion;

/**
 * An almost direct copy of net.minecraft.nbt.JsonToNBT, but strips quotes from field names.
 *
 */
public class JsonToNBT
{
    
    public static class NBTException extends Exception {
        public NBTException(String message) {
            super(message);
        }
    }

    //private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern INT_ARRAY_MATCHER = Pattern.compile("\\[[-+\\d|,\\s]+\\]");

    public static Object getTagFromJson(String jsonString) throws NBTException
    {
        jsonString = jsonString.trim();

        if (!jsonString.startsWith("{"))
        {
            throw new NBTException("Invalid tag encountered, expected \'{\' as first char.");
        }
        else if (topTagsCount(jsonString) != 1)
        {
            throw new NBTException("Encountered multiple top tags, only one expected");
        }
        else
        {
            return (Object)nameValueToNBT("tag", jsonString).parse();
        }
    }

    static int topTagsCount(String str) throws NBTException
    {
        int i = 0;
        boolean flag = false;
        Stack<Character> stack = new Stack();

        for (int j = 0; j < str.length(); ++j)
        {
            char c0 = str.charAt(j);

            if (c0 == 34)
            {
                if (isCharEscaped(str, j))
                {
                    if (!flag)
                    {
                        throw new NBTException("Illegal use of \\\": " + str);
                    }
                }
                else
                {
                    flag = !flag;
                }
            }
            else if (!flag)
            {
                if (c0 != 123 && c0 != 91)
                {
                    if (c0 == 125 && (stack.isEmpty() || stack.pop() != 123))
                    {
                        throw new NBTException("Unbalanced curly brackets {}: " + str);
                    }

                    if (c0 == 93 && (stack.isEmpty() || stack.pop() != 91))
                    {
                        throw new NBTException("Unbalanced square brackets []: " + str);
                    }
                }
                else
                {
                    if (stack.isEmpty())
                    {
                        ++i;
                    }

                    stack.push(c0);
                }
            }
        }

        if (flag)
        {
            throw new NBTException("Unbalanced quotation: " + str);
        }
        else if (!stack.isEmpty())
        {
            throw new NBTException("Unbalanced brackets: " + str);
        }
        else
        {
            if (i == 0 && !str.isEmpty())
            {
                i = 1;
            }

            return i;
        }
    }

    static JsonToNBT.Any joinStrToNBT(String... args) throws NBTException
    {
        return nameValueToNBT(args[0], args[1]);
    }

    static JsonToNBT.Any nameValueToNBT(String key, String value) throws NBTException
    {
        value = value.trim();
        if (key.startsWith("\"") && key.endsWith("\"")) key = key.substring(1, key.length() - 1); // this is the only bit I changed

        if (value.startsWith("{"))
        {
            value = value.substring(1, value.length() - 1);
            JsonToNBT.Compound jsontonbt$compound;
            String s1;

            for (jsontonbt$compound = new JsonToNBT.Compound(key); value.length() > 0; value = value.substring(s1.length() + 1))
            {
                s1 = nextNameValuePair(value, true);

                if (s1.length() > 0)
                {
                    boolean flag1 = false;
                    jsontonbt$compound.tagList.add(getTagFromNameValue(s1, false));
                }

                if (value.length() < s1.length() + 1)
                {
                    break;
                }

                char c1 = value.charAt(s1.length());

                if (c1 != 44 && c1 != 123 && c1 != 125 && c1 != 91 && c1 != 93)
                {
                    throw new NBTException("Unexpected token \'" + c1 + "\' at: " + value.substring(s1.length()));
                }
            }

            return jsontonbt$compound;
        }
        else if (value.startsWith("[") && !INT_ARRAY_MATCHER.matcher(value).matches())
        {
            value = value.substring(1, value.length() - 1);
            JsonToNBT.List jsontonbt$list;
            String s;

            for (jsontonbt$list = new JsonToNBT.List(key); value.length() > 0; value = value.substring(s.length() + 1))
            {
                s = nextNameValuePair(value, false);

                if (s.length() > 0)
                {
                    boolean flag = true;
                    jsontonbt$list.tagList.add(getTagFromNameValue(s, true));
                }

                if (value.length() < s.length() + 1)
                {
                    break;
                }

                char c0 = value.charAt(s.length());

                if (c0 != 44 && c0 != 123 && c0 != 125 && c0 != 91 && c0 != 93)
                {
                    throw new NBTException("Unexpected token \'" + c0 + "\' at: " + value.substring(s.length()));
                }
            }

            return jsontonbt$list;
        }
        else
        {
            return new JsonToNBT.Primitive(key, value);
        }
    }

    private static JsonToNBT.Any getTagFromNameValue(String str, boolean isArray) throws NBTException
    {
        String s = locateName(str, isArray);
        String s1 = locateValue(str, isArray);
        return joinStrToNBT(new String[] { s, s1 });
    }

    private static String nextNameValuePair(String str, boolean isCompound) throws NBTException
    {
        int i = getNextCharIndex(str, ':');
        int j = getNextCharIndex(str, ',');

        if (isCompound)
        {
            if (i == -1)
            {
                throw new NBTException("Unable to locate name/value separator for string: " + str);
            }

            if (j != -1 && j < i)
            {
                throw new NBTException("Name error at: " + str);
            }
        }
        else if (i == -1 || i > j)
        {
            i = -1;
        }

        return locateValueAt(str, i);
    }

    private static String locateValueAt(String str, int index) throws NBTException
    {
        Stack<Character> stack = new Stack();
        int i = index + 1;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;

        for (int j = 0; i < str.length(); ++i)
        {
            char c0 = str.charAt(i);

            if (c0 == 34)
            {
                if (isCharEscaped(str, i))
                {
                    if (!flag)
                    {
                        throw new NBTException("Illegal use of \\\": " + str);
                    }
                }
                else
                {
                    flag = !flag;

                    if (flag && !flag2)
                    {
                        flag1 = true;
                    }

                    if (!flag)
                    {
                        j = i;
                    }
                }
            }
            else if (!flag)
            {
                if (c0 != 123 && c0 != 91)
                {
                    if (c0 == 125 && (stack.isEmpty() || stack.pop() != 123))
                    {
                        throw new NBTException("Unbalanced curly brackets {}: " + str);
                    }

                    if (c0 == 93 && (stack.isEmpty() || stack.pop() != 91))
                    {
                        throw new NBTException("Unbalanced square brackets []: " + str);
                    }

                    if (c0 == 44 && stack.isEmpty())
                    {
                        return str.substring(0, i);
                    }
                }
                else
                {
                    stack.push(c0);
                }
            }

            if (!Character.isWhitespace(c0))
            {
                if (!flag && flag1 && j != i)
                {
                    return str.substring(0, j + 1);
                }

                flag2 = true;
            }
        }

        return str.substring(0, i);
    }

    private static String locateName(String str, boolean isArray) throws NBTException
    {
        if (isArray)
        {
            str = str.trim();

            if (str.startsWith("{") || str.startsWith("["))
            {
                return "";
            }
        }

        int i = getNextCharIndex(str, ':');

        if (i == -1)
        {
            if (isArray)
            {
                return "";
            }
            else
            {
                throw new NBTException("Unable to locate name/value separator for string: " + str);
            }
        }
        else
        {
            return str.substring(0, i).trim();
        }
    }

    private static String locateValue(String str, boolean isArray) throws NBTException
    {
        if (isArray)
        {
            str = str.trim();

            if (str.startsWith("{") || str.startsWith("["))
            {
                return str;
            }
        }

        int i = getNextCharIndex(str, ':');

        if (i == -1)
        {
            if (isArray)
            {
                return str;
            }
            else
            {
                throw new NBTException("Unable to locate name/value separator for string: " + str);
            }
        }
        else
        {
            return str.substring(i + 1).trim();
        }
    }

    private static int getNextCharIndex(String str, char targetChar)
    {
        int i = 0;

        for (boolean flag = true; i < str.length(); ++i)
        {
            char c0 = str.charAt(i);

            if (c0 == 34)
            {
                if (!isCharEscaped(str, i))
                {
                    flag = !flag;
                }
            }
            else if (flag)
            {
                if (c0 == targetChar)
                {
                    return i;
                }

                if (c0 == 123 || c0 == 91)
                {
                    return -1;
                }
            }
        }

        return -1;
    }

    private static boolean isCharEscaped(String str, int index)
    {
        return index > 0 && str.charAt(index - 1) == 92 && !isCharEscaped(str, index - 1);
    }

    abstract static class Any
    {

        protected String json;

        /**
         * Parses the JSON string contained in this object.
         * @return an {@link NBTBase} which can be safely cast to the type represented by this class.
         */
        public abstract Object parse() throws NBTException;
    }
    
    private static class CompoundParse114 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.server.v1_14_R1.NBTTagCompound nbttagcompound = 
                    new net.minecraft.server.v1_14_R1.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.server.v1_14_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }
    
    private static class CompoundParse115 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.server.v1_15_R1.NBTTagCompound nbttagcompound = 
                    new net.minecraft.server.v1_15_R1.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.server.v1_15_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }
    
    private static class CompoundParse116 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.server.v1_16_R1.NBTTagCompound nbttagcompound = 
                    new net.minecraft.server.v1_16_R1.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.server.v1_16_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }
    
    private static class CompoundParse116R2 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.server.v1_16_R2.NBTTagCompound nbttagcompound = 
                    new net.minecraft.server.v1_16_R2.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.server.v1_16_R2.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }
    
    private static class CompoundParse116R3 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.server.v1_16_R3.NBTTagCompound nbttagcompound = 
                    new net.minecraft.server.v1_16_R3.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.server.v1_16_R3.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }
    
    private static class CompoundParse117R1 {
        public Object parse(Compound c) throws NBTException {
            net.minecraft.nbt.NBTTagCompound nbttagcompound = 
                    new net.minecraft.nbt.NBTTagCompound();

            for (JsonToNBT.Any jsontonbt$any : c.tagList) {
                nbttagcompound.set(jsontonbt$any.json, 
                        (net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
            }

            return nbttagcompound;
        }
    }

    static class Compound extends JsonToNBT.Any
    {

        protected java.util.List<JsonToNBT.Any> tagList = Lists.<JsonToNBT.Any>newArrayList();

        public Compound(String jsonIn)
        {
            this.json = jsonIn;
        }

        /**
         * Parses the JSON string contained in this object.
         * @return an {@link NBTBase} which can be safely cast to the type represented by this class.
         */
        @Override
        public Object parse() throws NBTException
        {
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                return (new CompoundParse114()).parse(this);
            }
            
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                return (new CompoundParse115()).parse(this);
            }
            
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                return (new CompoundParse116()).parse(this);
            }
            
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                return (new CompoundParse116R2()).parse(this);
            }
            
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                return (new CompoundParse116R3()).parse(this);
            }
            
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                return (new CompoundParse117R1()).parse(this);
            }
                    
            return null;
        }
    }
    
    private static class ListParse114 {
        public Object parse(List l) throws NBTException {
            net.minecraft.server.v1_14_R1.NBTTagList nbttaglist = 
                                new net.minecraft.server.v1_14_R1.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.server.v1_14_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }
    
    private static class ListParse115 {
        public Object parse(List l) throws NBTException {
            net.minecraft.server.v1_15_R1.NBTTagList nbttaglist = 
                                new net.minecraft.server.v1_15_R1.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.server.v1_15_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }
    
    private static class ListParse116 {
        public Object parse(List l) throws NBTException {
            net.minecraft.server.v1_16_R1.NBTTagList nbttaglist = 
                                new net.minecraft.server.v1_16_R1.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.server.v1_16_R1.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }
    
    private static class ListParse116R2 {
        public Object parse(List l) throws NBTException {
            net.minecraft.server.v1_16_R2.NBTTagList nbttaglist = 
                                new net.minecraft.server.v1_16_R2.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.server.v1_16_R2.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }
    
    private static class ListParse116R3 {
        public Object parse(List l) throws NBTException {
            net.minecraft.server.v1_16_R3.NBTTagList nbttaglist = 
                                new net.minecraft.server.v1_16_R3.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.server.v1_16_R3.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }
    
    private static class ListParse117R1 {
        public Object parse(List l) throws NBTException {
            net.minecraft.nbt.NBTTagList nbttaglist = 
                                new net.minecraft.nbt.NBTTagList();

            for (JsonToNBT.Any jsontonbt$any : l.tagList)
            {
                nbttaglist.add((net.minecraft.nbt.NBTBase) jsontonbt$any.parse());
            }

            return nbttaglist;
        }
    }

    static class List extends JsonToNBT.Any
    {

        protected java.util.List<JsonToNBT.Any> tagList = Lists.<JsonToNBT.Any>newArrayList();

        public List(String json)
        {
            this.json = json;
        }

        /**
         * Parses the JSON string contained in this object.
         * @return an {@link NBTBase} which can be safely cast to the type represented by this class.
         */
        @Override
        public Object parse() throws NBTException
        {
            if(Main.version == MultiVersion.Version.V1_14_R1) {
                return (new ListParse114()).parse(this);
            }
            if(Main.version == MultiVersion.Version.V1_15_R1) {
                return (new ListParse115()).parse(this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R1) {
                return (new ListParse116()).parse(this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R2) {
                return (new ListParse116R2()).parse(this);
            }
            if(Main.version == MultiVersion.Version.V1_16_R3) {
                return (new ListParse116R3()).parse(this);
            }
            if(Main.version == MultiVersion.Version.V1_17_R1) {
                return (new ListParse117R1()).parse(this);
            }
            return null;
        }
    }
    
    private static class PrimitiveParse114 {
        public Object parseDouble(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagDouble(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagFloat(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagByte(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagLong(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagShort(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagInt(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagDouble(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagByte(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagString(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.server.v1_14_R1.NBTTagIntArray(aint);
        }
    }
    
    private static class PrimitiveParse115 {
        public Object parseDouble(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagFloat.a(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagByte.a(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagLong.a(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagShort.a(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagInt.a(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagByte.a(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return net.minecraft.server.v1_15_R1.NBTTagString.a(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.server.v1_15_R1.NBTTagIntArray(aint);
        }
    }
    
    private static class PrimitiveParse116 {
        public Object parseDouble(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagFloat.a(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagByte.a(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagLong.a(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagShort.a(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagInt.a(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagByte.a(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return net.minecraft.server.v1_16_R1.NBTTagString.a(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.server.v1_16_R1.NBTTagIntArray(aint);
        }
    }
    
    private static class PrimitiveParse116R2 {
        public Object parseDouble(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagFloat.a(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagByte.a(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagLong.a(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagShort.a(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagInt.a(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagByte.a(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return net.minecraft.server.v1_16_R2.NBTTagString.a(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.server.v1_16_R2.NBTTagIntArray(aint);
        }
    }
    
    private static class PrimitiveParse116R3 {
        public Object parseDouble(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagFloat.a(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagByte.a(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagLong.a(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagShort.a(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagInt.a(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagByte.a(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return net.minecraft.server.v1_16_R3.NBTTagString.a(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.server.v1_16_R3.NBTTagIntArray(aint);
        }
    }
    
    private static class PrimitiveParse117R1 {
        public Object parseDouble(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseFloat(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagFloat.a(
                    Float.parseFloat(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseByte(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagByte.a(
                    Byte.parseByte(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseLong(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagLong.a(
                    Long.parseLong(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseShort(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagShort.a(
                    Short.parseShort(p.jsonValue.substring(0, p.jsonValue.length() - 1))
            );
        }
        public Object parseInteger(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagInt.a(
                    Integer.parseInt(p.jsonValue)
            );
        }
        public Object parseDoubleUntyped(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagDouble.a(
                    Double.parseDouble(p.jsonValue)
            );
        }
        public Object parseBoolean(Primitive p) throws NBTException {
            return net.minecraft.nbt.NBTTagByte.a(
                    (byte)(Boolean.parseBoolean(p.jsonValue) ? 1 : 0)
            );
        }
        public Object parseString(String str) throws NBTException {
            return net.minecraft.nbt.NBTTagString.a(str);
        }
        public Object parseIntArray(Primitive p, int[] aint) throws NBTException {
            return new net.minecraft.nbt.NBTTagIntArray(aint);
        }
    }

    static class Primitive extends JsonToNBT.Any
    {

        private static final Pattern DOUBLE = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[d|D]");
        private static final Pattern FLOAT = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+[f|F]");
        private static final Pattern BYTE = Pattern.compile("[-+]?[0-9]+[b|B]");
        private static final Pattern LONG = Pattern.compile("[-+]?[0-9]+[l|L]");
        private static final Pattern SHORT = Pattern.compile("[-+]?[0-9]+[s|S]");
        private static final Pattern INTEGER = Pattern.compile("[-+]?[0-9]+");
        private static final Pattern DOUBLE_UNTYPED = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
        private static final Splitter SPLITTER = Splitter.on(',').omitEmptyStrings();
        /** The value to be parsed into a tag. */
        protected String jsonValue;

        public Primitive(String jsonIn, String valueIn)
        {
            this.json = jsonIn;
            this.jsonValue = valueIn;
        }

        /**
         * Parses the JSON string contained in this object.
         * @return an {@link NBTBase} which can be safely cast to the type represented by this class.
         */
        @Override
        public Object parse() throws NBTException
        {
            try
            {
                if (DOUBLE.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseDouble(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return net.minecraft.server.v1_15_R1.NBTTagDouble.a(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return net.minecraft.server.v1_16_R1.NBTTagDouble.a(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return net.minecraft.server.v1_16_R2.NBTTagDouble.a(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return net.minecraft.server.v1_16_R3.NBTTagDouble.a(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return net.minecraft.nbt.NBTTagDouble.a(Double.parseDouble(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    return null;
                }

                if (FLOAT.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseFloat(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return net.minecraft.server.v1_15_R1.NBTTagFloat.a(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return net.minecraft.server.v1_16_R1.NBTTagFloat.a(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return net.minecraft.server.v1_16_R2.NBTTagFloat.a(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return net.minecraft.server.v1_16_R3.NBTTagFloat.a(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return net.minecraft.nbt.NBTTagFloat.a(Float.parseFloat(this.jsonValue.substring(0, this.jsonValue.length() - 1)));
                    }
                    return null;
                }

                if (BYTE.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseByte(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseByte(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseByte(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseByte(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseByte(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseByte(this);
                    }
                    return null;
                }

                if (LONG.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseLong(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseLong(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseLong(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseLong(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseLong(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseLong(this);
                    }
                    return null;
                }

                if (SHORT.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseShort(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseShort(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseShort(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseShort(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseShort(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseShort(this);
                    }
                    return null;
                }

                if (INTEGER.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseInteger(this); 
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseInteger(this); 
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseInteger(this); 
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseInteger(this); 
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseInteger(this); 
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseInteger(this); 
                    }
                    return null;
                }

                if (DOUBLE_UNTYPED.matcher(this.jsonValue).matches())
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseDoubleUntyped(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseDoubleUntyped(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseDoubleUntyped(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseDoubleUntyped(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseDoubleUntyped(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseDoubleUntyped(this);
                    }
                    return null;
                }

                if ("true".equalsIgnoreCase(this.jsonValue) || "false".equalsIgnoreCase(this.jsonValue))
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseBoolean(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseBoolean(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseBoolean(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseBoolean(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseBoolean(this);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseBoolean(this);
                    }
                    return null;
                }
            }
            catch (NumberFormatException var6)
            {
                this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
                if(Main.version == MultiVersion.Version.V1_14_R1) {
                    return (new PrimitiveParse114()).parseString(this.jsonValue);
                }
                if(Main.version == MultiVersion.Version.V1_15_R1) {
                    return (new PrimitiveParse115()).parseString(this.jsonValue);
                }
                if(Main.version == MultiVersion.Version.V1_16_R1) {
                    return (new PrimitiveParse116()).parseString(this.jsonValue);
                }
                if(Main.version == MultiVersion.Version.V1_16_R2) {
                    return (new PrimitiveParse116R2()).parseString(this.jsonValue);
                }
                if(Main.version == MultiVersion.Version.V1_16_R3) {
                    return (new PrimitiveParse116R3()).parseString(this.jsonValue);
                }
                if(Main.version == MultiVersion.Version.V1_17_R1) {
                    return (new PrimitiveParse117R1()).parseString(this.jsonValue);
                }
                return null;
            }

            if (this.jsonValue.startsWith("[") && this.jsonValue.endsWith("]"))
            {
                String s = this.jsonValue.substring(1, this.jsonValue.length() - 1);
                String[] astring = Iterables.toArray(SPLITTER.split(s), String.class);

                try
                {
                    int[] aint = new int[astring.length];

                    for (int j = 0; j < astring.length; ++j) {
                        aint[j] = Integer.parseInt(astring[j].trim());
                    }
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseIntArray(this, aint);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseIntArray(this, aint);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseIntArray(this, aint);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseIntArray(this, aint);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseIntArray(this, aint);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseIntArray(this, aint);
                    }
                    return null;
                }
                catch (NumberFormatException var5)
                {
                    if(Main.version == MultiVersion.Version.V1_14_R1) {
                        return (new PrimitiveParse114()).parseString(this.jsonValue);
                    }
                    if(Main.version == MultiVersion.Version.V1_15_R1) {
                        return (new PrimitiveParse115()).parseString(this.jsonValue);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R1) {
                        return (new PrimitiveParse116()).parseString(this.jsonValue);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R2) {
                        return (new PrimitiveParse116R2()).parseString(this.jsonValue);
                    }
                    if(Main.version == MultiVersion.Version.V1_16_R3) {
                        return (new PrimitiveParse116R3()).parseString(this.jsonValue);
                    }
                    if(Main.version == MultiVersion.Version.V1_17_R1) {
                        return (new PrimitiveParse117R1()).parseString(this.jsonValue);
                    }
                    return null;
                }
            }
            else
            {
                if (this.jsonValue.startsWith("\"") && this.jsonValue.endsWith("\""))
                {
                    this.jsonValue = this.jsonValue.substring(1, this.jsonValue.length() - 1);
                }

                this.jsonValue = this.jsonValue.replaceAll("\\\\\"", "\"");
                StringBuilder stringbuilder = new StringBuilder();

                for (int i = 0; i < this.jsonValue.length(); ++i)
                {
                    if (i < this.jsonValue.length() - 1 && this.jsonValue.charAt(i) == 92 && this.jsonValue.charAt(i + 1) == 92)
                    {
                        stringbuilder.append('\\');
                        ++i;
                    }
                    else
                    {
                        stringbuilder.append(this.jsonValue.charAt(i));
                    }
                }
                if(Main.version == MultiVersion.Version.V1_14_R1) {
                    return (new PrimitiveParse114()).parseString(stringbuilder.toString());
                }
                if(Main.version == MultiVersion.Version.V1_15_R1) {
                    return (new PrimitiveParse115()).parseString(stringbuilder.toString());
                }
                if(Main.version == MultiVersion.Version.V1_16_R1) {
                    return (new PrimitiveParse116()).parseString(stringbuilder.toString());
                }
                if(Main.version == MultiVersion.Version.V1_16_R2) {
                    return (new PrimitiveParse116R2()).parseString(stringbuilder.toString());
                }
                if(Main.version == MultiVersion.Version.V1_16_R3) {
                    return (new PrimitiveParse116R3()).parseString(stringbuilder.toString());
                }
                if(Main.version == MultiVersion.Version.V1_17_R1) {
                    return (new PrimitiveParse117R1()).parseString(stringbuilder.toString());
                }
                return null;
            }
        }
    }
}