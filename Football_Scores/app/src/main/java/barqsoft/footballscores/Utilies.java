package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 401;
    public static final int PREMIER_LEAGUE = 398;
    public static final int PRIMERA_DIVISION = 399;
    public static final int BUNDESLIGA1 = 394;
    public static final int BUNDESLIGA2 = 395;
    public static String getLeague(Context context, int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return context.getString(R.string.league_serie_a);
            case PREMIER_LEAGUE : return context.getString(R.string.league_premier_league);
            case PRIMERA_DIVISION : return context.getString(R.string.league_primera_division);
            case BUNDESLIGA1 :
            case BUNDESLIGA2 : return context.getString(R.string.league_bundesliga);
            default: return context.getString(R.string.league_unknown);
        }
    }
    public static String getScores(Context context, int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return context.getString(R.string.scores_empty);
        }
        else
        {
            return context.getString(R.string.scores, String.valueOf(home_goals), String.valueOf(awaygoals));
        }
    }

    public static String getScoreDescription(Context context, int home_goals, int away_goals) {
        if (home_goals < 0 || away_goals < 0) {
            return context.getString(R.string.not_played_description);
        } else {
            return context.getString(R.string.scores_description, String.valueOf(home_goals),
                    String.valueOf(away_goals));
        }
    }

    public static int getTeamCrestByTeamName (Context context, String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.no_icon;
        }
    }
}
