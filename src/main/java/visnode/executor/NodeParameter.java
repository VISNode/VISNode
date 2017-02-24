/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.executor;

import java.util.Objects;

/**
 *
 */
public class NodeParameter {
    
    private final String name;
    
    private final Class type;

    public NodeParameter(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NodeParameter other = (NodeParameter) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }
    
    
    
}
