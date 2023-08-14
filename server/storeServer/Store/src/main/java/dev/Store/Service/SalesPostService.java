package dev.Store.Service;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface SalesPostService {
    public JSONObject create(JSONObject jsonObject);
    public JSONObject read(JSONObject jsonObject);
    public JSONObject readAll();
    public JSONObject update(JSONObject jsonObject);
    public JSONObject delete(JSONObject jsonObject);
}
