package org.darkrpa.discord.bots.june.events;

import java.util.List;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

public class AyudaMenuListener extends AbstractEventListener{

    public AyudaMenuListener(JDA bot) {
        super(bot);
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof SelectMenuInteractionEvent){
            SelectMenuInteractionEvent evento = (SelectMenuInteractionEvent)event;
            List<SelectOption> lista = evento.getSelectedOptions();
            evento.editMessage(lista.get(0).getLabel()).queue();
        }
    }

}
