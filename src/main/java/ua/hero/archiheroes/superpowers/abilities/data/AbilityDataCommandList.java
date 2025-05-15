package ua.hero.archiheroes.superpowers.abilities.data;

import com.google.gson.JsonObject;
import lucraft.mods.lucraftcore.superpowers.abilities.data.AbilityData;
import ua.hero.archiheroes.superpowers.abilities.AbilityCommand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.util.Constants;

public class AbilityDataCommandList extends AbilityData<AbilityCommand.CommandList> {

    public AbilityDataCommandList(String key) {
        super(key);
    }

    @Override
    public AbilityCommand.CommandList parseValue(JsonObject jsonObject, AbilityCommand.CommandList defaultValue) {
        if (!JsonUtils.hasField(jsonObject, this.jsonKey))
            return defaultValue;
        return new AbilityCommand.CommandList(jsonObject.get(this.jsonKey));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt, AbilityCommand.CommandList value) {
        nbt.setTag(this.key, value.serializeNBT());
    }

    @Override
    public AbilityCommand.CommandList readFromNBT(NBTTagCompound nbt, AbilityCommand.CommandList defaultValue) {
        if (!nbt.hasKey(this.key))
            return defaultValue;
        AbilityCommand.CommandList list = new AbilityCommand.CommandList();
        list.deserializeNBT(nbt.getTagList(this.key, Constants.NBT.TAG_STRING));
        return list;
    }

    @Override
    public boolean displayAsString(AbilityCommand.CommandList value) {
        return false;
    }

    @Override
    public String getDisplay(AbilityCommand.CommandList value) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < value.getCommands().size(); i++) {
            stringBuilder.append("\"").append(value.getCommands().get(i)).append("\"");
            if (i < value.getCommands().size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
