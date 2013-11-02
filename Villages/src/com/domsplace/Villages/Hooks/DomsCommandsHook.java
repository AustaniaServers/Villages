/*
 * Copyright 2013 Dominic.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.domsplace.Villages.Hooks;

import com.domsplace.Villages.Bases.PluginHook;
import com.domsplace.Villages.Listeners.DomsCommandsListener;

/**
 * @author      Dominic
 * @since       02/11/2013
 */
public class DomsCommandsHook extends PluginHook {
    private DomsCommandsListener listener;
    
    public DomsCommandsHook() {
        super("DomsCommands");
    }
    
    @Override
    public void onHook() {
        super.onHook();
        if(this.listener == null) this.listener = new DomsCommandsListener();
    }
    
    @Override
    public void onUnhook() {
        super.onUnhook();
        if(this.listener != null) {
            this.listener.deRegisterListener();
            this.listener = null;
        }
    }
}
