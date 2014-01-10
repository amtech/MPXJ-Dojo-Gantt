/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gagravarr.mpxj.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskContainer;

public class DojoGanttJSON {
    public static String toJSON(ProjectFile project) throws IOException {
        StringWriter writer = new StringWriter();
        toJSON(project, writer);
        return writer.toString();
    }
    
    public static void toJSON(ProjectFile project, Writer out) throws IOException {
        JSONObject json = new JSONObject();
        
        // Boiler plate
        json.put("identifier", ID);        
        
        // Start from the root
        JSONArray items = new JSONArray();
        handleTaskContainer(project, items);
        json.put("items", items);
        
        // Have it output as JSON
        json.writeJSONString(out);
    }
    
    protected static final String ID = "id";
    protected static void handleTaskContainer(TaskContainer tasks, JSONArray items) {
        for (Task task : tasks.getChildTasks()) {
            JSONObject json = new JSONObject();
            json.put(ID, task.getUniqueID());
            json.put("name", task.getName());
            
            // TODO The rest
            
            JSONArray children = new JSONArray();
            handleTaskContainer(task, children);
            json.put("children", children);
            
            items.add(json);
        }
    }
}