package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MonsterCard implements Card {
    
    // Construtor padrão (necessário para a MatchWindow rodar o teste)
    public MonsterCard() {
    }

    @Override
    public String getName() {
        return "Tropa 1 (Teste)";
    }

    @Override
    public String getDescription() {
        
        return "Carta de teste para validar o painel de zoom e as ações dinâmicas .";
    }

    @Override
    public String getImagePath() {
        return "/images/placeholder.png";
    }

    /**
     * Retorna a lista de ações disponíveis. 
     */
    @Override
    public List<GameAction> getAvailableActions() {
        
        List<GameAction> actions = new ArrayList<>();
        
        // Ação 1
        actions.add(new GameAction() {
            @Override
            public String getLabel() { return "Ação 1"; }
            @Override
            public void execute() { 
                System.out.println("LOG: AÇÃO 1 EXECUTADA"); 
            }
        });
        
        // Ação 2
        actions.add(new GameAction() {
            @Override
            public String getLabel() { return "Ação 2"; } 
            @Override
            public void execute() { 
                System.out.println("LOG: AÇÃO 2 EXECUTADA"); 
            }
        });

        
        return Collections.unmodifiableList(actions);
    }
}