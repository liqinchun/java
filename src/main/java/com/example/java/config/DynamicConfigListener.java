package com.example.java.config;

import com.example.java.event.DynamicConfigEvent;
import com.google.common.annotations.Beta;
import org.onosproject.event.EventListener;

/**
 * Entity capable of receiving dynamic config change events.
 */
@Beta
public interface DynamicConfigListener extends EventListener<DynamicConfigEvent> {
}


