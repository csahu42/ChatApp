package com.example.chhattlal.chatapp.data;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import java.util.Locale;

public final class Migration implements RealmMigration {

  @Override public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
    RealmSchema schema = realm.getSchema();

    if (oldVersion == 0) {
   /*  schema.create("User")
         .addField("userId", long.class)
         .addField("userName", String.class)
         .addField("picURL", String.class)
          .addRealmListField("messages", schema.get("Message"));*/
      oldVersion++;
    }

    if (oldVersion < newVersion) {
      throw new IllegalStateException(
          String.format(Locale.ENGLISH, "Migration missing from v%d to v%d", oldVersion,
              newVersion));
    }
  }
}
