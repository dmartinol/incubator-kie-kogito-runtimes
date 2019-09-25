/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.kogito.codegen.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.test.util.NodeLeftCountDownProcessEventListener;
import org.junit.jupiter.api.Test;
import org.kie.kogito.Application;
import org.kie.kogito.Model;
import org.kie.kogito.codegen.AbstractCodegenTest;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.impl.DefaultProcessEventListenerConfig;


public class TimerEventTest extends AbstractCodegenTest {
    
    
    @Test
    public void testIntermediateCycleTimerEvent() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/IntermediateCatchEventTimerCycleISO.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("timer", 3);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("IntermediateCatchEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
        
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_ACTIVE);        
        processInstance.abort();
        
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_ABORTED);
    }
    
    @Test
    public void testIntermediateDurationTimerEvent() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/IntermediateCatchEventTimerDurationISO.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("timer", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("IntermediateCatchEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
    
    @Test
    public void testIntermediateDateTimerEvent() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/IntermediateCatchEventTimerDateISO.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("timer", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("IntermediateCatchEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        OffsetDateTime plusTwoSeconds = OffsetDateTime.now().plusSeconds(2);
        parameters.put("date", plusTwoSeconds.toString());
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
    
    @Test
    public void testBoundaryDurationTimerEventOnTask() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/TimerBoundaryEventDurationISOOnTask.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("TimerEvent", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("TimerBoundaryEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
    
    @Test
    public void testBoundaryCycleTimerEventOnTask() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/TimerBoundaryEventCycleISOOnTask.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("TimerEvent", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("TimerBoundaryEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
    
    @Test
    public void testBoundaryDateTimerEventOnTask() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/TimerBoundaryEventDateISOOnTask.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("TimerEvent", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("TimerBoundaryEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        OffsetDateTime plusTwoSeconds = OffsetDateTime.now().plusSeconds(2);
        parameters.put("date", plusTwoSeconds.toString());
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
    
    @Test
    public void testBoundaryDurationTimerEventOnSubprocess() throws Exception {
        
        Application app = generateCodeProcessesOnly("timer/TimerBoundaryEventDurationISO.bpmn2");        
        assertThat(app).isNotNull();
        
        NodeLeftCountDownProcessEventListener listener = new NodeLeftCountDownProcessEventListener("TimerEvent", 1);
        ((DefaultProcessEventListenerConfig)app.config().process().processEventListeners()).register(listener);
                
        Process<? extends Model> p = app.processes().processById("TimerBoundaryEvent");
        
        Model m = p.createModel();
        Map<String, Object> parameters = new HashMap<>();
        m.fromMap(parameters);
        
        ProcessInstance<?> processInstance = p.createInstance(m);
        processInstance.start();
        
        boolean completed = listener.waitTillCompleted(5000);
        assertThat(completed).isTrue();
     
        assertThat(processInstance.status()).isEqualTo(ProcessInstance.STATE_COMPLETED);
    }
}
