package model.Ledger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Ledger {

    private List<LedgerEntry> _entries;

    public Ledger() {}

    @JsonIgnore
    public List<LedgerEntry> getEntries() {
        return _entries;
    }

    @JsonProperty("Entries")
    public void setEntries(List<LedgerEntry> entries) {
        this._entries = entries;
    }
}
