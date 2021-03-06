package com.teinelund.jproject_info.strategy;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.fusesource.jansi.Ansi;

import java.io.FileReader;
import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;

class VersionProvider {
    public String[] getVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        try {
            model = reader.read(new FileReader("pom.xml"));
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return new String[]{"Could not fetch version from application."};
        }
        return new String[]{ ansi().fg(Ansi.Color.WHITE).a("Version: " + model.getVersion()).toString(), ansi().fg(Ansi.Color.YELLOW).a("Copyright (C) Teinelund 2020.").toString() };
    }
}
