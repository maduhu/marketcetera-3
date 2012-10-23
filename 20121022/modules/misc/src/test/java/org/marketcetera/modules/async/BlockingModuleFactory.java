package org.marketcetera.modules.async;

import org.marketcetera.core.util.log.I18NMessage0P;
import org.marketcetera.core.module.ModuleFactory;
import org.marketcetera.core.module.Module;
import org.marketcetera.core.module.ModuleCreationException;
import org.marketcetera.core.module.ModuleURN;

/* $License$ */
/**
 * Factory for a module that blocks when receiving data.
 *
 * @version $Id: BlockingModuleFactory.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.0.0
 */
public class BlockingModuleFactory extends ModuleFactory {
    /**
     * Creates an instance.
     *
     */
    public BlockingModuleFactory() {
        super(PROVIDER_URN, new I18NMessage0P(Messages.LOGGER,
                "blockingFactory"), false, false);
    }

    @Override
    public Module create(Object... inParameters) throws ModuleCreationException {
        return sLastInstance = new BlockingReceiverModule();
    }

    /**
     * The last module instance created by this factory.
     * @return the last module instance created by this factory.
     */
    public static BlockingReceiverModule getLastInstance() {
        return sLastInstance;
    }

    private volatile static BlockingReceiverModule sLastInstance;
    /**
     * The Provider URN.
     */
    public static final ModuleURN PROVIDER_URN =
            new ModuleURN("metc:test:blocking");  //$NON-NLS-1$
    public static final ModuleURN INSTANCE_URN =
            new ModuleURN(PROVIDER_URN, "single");  //$NON-NLS-1$

}