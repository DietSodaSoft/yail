package com.dietsodasoftware;

import java.util.Arrays;

/**
 * User: wendel.schultz
 * Date: 4/30/14
 */
public class DopTicketThing {

    private static final String[] blocks = new String[] {
             "mn151"
            ,"mn117"
            ,"mn18"
            ,"mn76"
            ,"mn63"
            ,"mn40"
            ,"mn81"
            ,"mn77"
            ,"mn122"
            ,"mn52"
            ,"mn75"
            ,"mn80"
            ,"mn85"
            ,"mn13"
            ,"mn112"
            ,"mn43"
            ,"mn114"
            ,"mn42"
            ,"mn41"
            ,"mn4"
            ,"mn111"
            ,"mn60"
            ,"mn36"
            ,"mn98"
            ,"mn88"
            ,"mn15"
            ,"mn37"
            ,"mn89"
            ,"mn132"
            ,"mn2"
            ,"mn126"
            ,"mn133"
            ,"mn38"
            ,"mn64"
            ,"mn61"
            ,"mn153"
            ,"mn123"
            ,"mn93"
            ,"mn125"
            ,"mn79"
            ,"mn48"
            ,"mn102"
            ,"mn82"
            ,"mn87"
            ,"mn83"
            ,"mn97"
            ,"mn158"
            ,"mn120"
            ,"mn129"
            ,"mn108"
            ,"mn152"
            ,"mn106"
            ,"mn84"
            ,"mn47"
            ,"mn62"
            ,"mn90"
            ,"mn105"
            ,"mn30"
            ,"mn16"
            ,"mn59"
    };

    // this list of strings are the block names
    private static final String[] blocks129 = new String[]{
             "mn78"
            ,"mn34"
            ,"mn109"
            ,"mn1"
            ,"mn159"
            ,"mn100"
            ,"mn35"
            ,"mn107"
            ,"mn33"
            ,"mn53"
            ,"mn94"
            ,"mn29"
            ,"mn113"
            ,"mn135"
            ,"mn67"
            ,"mn157"
            ,"mn128"
            ,"mn127"
            ,"mn8"
            ,"mn46"
            ,"mn71"
            ,"mn45"
            ,"mn50"
            ,"mn31"
            ,"mn44"
            ,"mn17"
            ,"mn154"
            ,"mn104"
            ,"mn115"
            ,"mn73"
            ,"mn66"
            ,"mn9"
            ,"mn72"
            ,"mn101"
            ,"mn28"
            ,"mn12"
            ,"mn10"
            ,"mn65"
            ,"mn150"
            ,"mn69"
            ,"mn58"
            ,"mn92"
            ,"mn70"
            ,"mn57"
            ,"mn54"
            ,"mn22"
            ,"mn51"
            ,"mn156"
            ,"mn74"
            ,"mn103"
            ,"mn110"
            ,"mn19"
            ,"mn32"
            ,"mn55"
            ,"mn6"
            ,"mn56"
            ,"mn134"
            ,"mn95"
            ,"mn5"
            ,"mn155"
            ,"mn121"
            ,"mn96"
            ,"mn27"
            ,"mn7"
            ,"mn86"
            ,"mn131"
            ,"mn14"
            ,"mn130"
            ,"mn99"
            ,"mn138"
            ,"mn11"
            ,"mn137"
            ,"mn124"
            ,"mn20"
            ,"mn68"
            ,"mn149"
            ,"mn24"
            ,"mn3"
            ,"mn148"
            ,"mn119"
            ,"mn116"
            ,"mn25"
            ,"mn141"
            ,"mn21"
            ,"mn144"
            ,"mn39"
            ,"mn136"
            ,"mn145"
            ,"en2"
            ,"mn49"
            ,"mn23"
            ,"mn142"
            ,"mn143"
            ,"mn26"
            ,"mn146"
            ,"mn147"
            ,"mn139"
            ,"mn91"
            ,"mn171"
            ,"mn166"
            ,"mn164"
            ,"ft2"
            ,"mn163"
            ,"en11"
            ,"mn168"
            ,"mn140"
            ,"mn170"
            ,"mn167"
            ,"mn169"
            ,"en4"
            ,"en12"
            ,"ml1"
            ,"mn165"
            ,"mn161"
            ,"en16"
            ,"mn160"
            ,"en15"
            ,"mn162"
            ,"en20"
            ,"en24"
            ,"en3"
            ,"en23"
            ,"en19"
            ,"en17"
            ,"en25"
            ,"en5"
            ,"en6"
            ,"en22"
            ,"en7"

    };

    public static void main(String [] args){

        new DopTicketThing().generateTicketContent();
    }

    private void thing(){

        try{
            throw new IllegalArgumentException("I don't like your argument");
        } finally {
            throw new IllegalStateException("Arizona is not legal");
        }

    }

    private void generateTicketContent(){
        String[] theBlocks = blocks129;

        System.out.println("Configuring metric publishing is documented here: http://confluence.infusionsoft.com/confluence/display/dev/Enabling+InfusionsoftCRM+Metrics+Collection");
        System.out.println("Please enable enterprise metrics using the CRMMaster Database method for an additional " +theBlocks.length+ " blocks:");

        Arrays.sort(theBlocks);
        for(String block: theBlocks){
            System.out.println("* " + block);
        }
        System.out.println();

        System.out.println("The SQL to accomplish this follows. Note that this should be run against CRMMaster.");

        // the template is:
//        DELETE FROM Resources WHERE ResourceName in ('MetricScopeEnterpriseRoleEnabled', 'MetricScopeEnterpriseEnabled') AND Block = 'en10';
//        INSERT INTO Resources (ResourceName, ResourceValue, Block) VALUES ('MetricScopeEnterpriseRoleEnabled', 'true', 'en10');
//        INSERT INTO Resources (ResourceName, ResourceValue, Block) VALUES ('MetricScopeEnterpriseEnabled', 'true', 'en10');


        System.out.println("{code}");
        for(String block: theBlocks){

            final String delete         = String.format("DELETE FROM Resources WHERE ResourceName in ('MetricScopeEnterpriseRoleEnabled', 'MetricScopeEnterpriseEnabled') AND Block = '%s';", block);
            final String enterpriseRole = String.format("INSERT INTO Resources (ResourceName, ResourceValue, Block) VALUES ('MetricScopeEnterpriseRoleEnabled', 'true', '%s');", block);
            final String enterprise     = String.format("INSERT INTO Resources (ResourceName, ResourceValue, Block) VALUES ('MetricScopeEnterpriseEnabled', 'true', '%s');", block);

            final String deleteFrequency = String.format("DELETE FROM Resources WHERE ResourceName in ('HostedGraphitePublishingFrequency') AND Block = '%s';", block);
            final String frequency       = String.format("INSERT INTO Resources (ResourceName, ResourceValue, Block) VALUES ('HostedGraphitePublishingFrequency', '1', '%s');", block);

            System.out.println("-- block " + block);
            System.out.println(deleteFrequency);
            System.out.println(delete);
            System.out.println(enterpriseRole);
            System.out.println(enterprise);
            System.out.println(frequency);

            System.out.println();
        }
        System.out.println("{code}");
    }

}


