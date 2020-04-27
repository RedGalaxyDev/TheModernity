package modernity.plugin;

import net.minecraftforge.api.distmarker.Dist;
import org.objectweb.asm.Type;

class ASMPluginData {
    private final Type classType;
    private final Dist dist;

    ASMPluginData( Type classType, Dist dist ) {
        this.classType = classType;
        this.dist = dist;
    }

    boolean shouldLoad( Dist actualDist ) {
        return dist == null || dist == actualDist;
    }

    PluginHolder makeHolder() throws PluginException {
        try {
            Class<?> cls = Class.forName( classType.getClassName() );
            return new PluginHolder( cls );
        } catch( ClassNotFoundException exc ) {
            throw new PluginException( "Unable to find plugin class: " + classType.getClassName() );
        }
    }
}