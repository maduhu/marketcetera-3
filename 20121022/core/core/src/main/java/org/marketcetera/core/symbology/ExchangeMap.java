package org.marketcetera.core.symbology;

import java.util.HashMap;
import java.util.Map;

/**
 * @version $Id: ExchangeMap.java 16063 2012-01-31 18:21:55Z colin $
 */
public class ExchangeMap {
    private SymbolScheme scheme;
    private Map<String, Exchange> schemeToStandardTranslation;
    private Map<String, String> standardToSchemeTranslation = new HashMap<String, String>();

    protected ExchangeMap()
    {
    }

    public ExchangeMap(SymbolScheme scheme, Map<String, Exchange> schemeToStandardTranslation) {
        this.scheme = scheme;
        init(schemeToStandardTranslation);
    }

    protected void init(Map<String, Exchange> schemeToStandardTranslation) {
        this.schemeToStandardTranslation = schemeToStandardTranslation;
        for (String s : schemeToStandardTranslation.keySet()) {
            String key = (String) s;
            standardToSchemeTranslation.put(schemeToStandardTranslation.get(key).getMarketIdentifierCode(), key);
        }
    }


    public SymbolScheme getScheme() {
        return scheme;
    }

    public Exchange getExchange(String schemeName){
        return schemeToStandardTranslation.get(schemeName);
    }

    public String getSchemeName(Exchange exch){
        return standardToSchemeTranslation.get(exch.getMarketIdentifierCode());
    }

    public String getSchemeName(String marketIdentifierCode){
        return standardToSchemeTranslation.get(marketIdentifierCode);
    }
}