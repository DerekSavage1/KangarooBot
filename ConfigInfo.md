# This file will break down all of the current config options as of v1.0

## Mandatory
### discordMinecraftChannelID
 - This field is for the channel id of the main discord channel used in this plugin.
 - To get this id, make sure developer mode is turned on in discord, right-click on the text channel, and select "Copy ID"
 - This should be a string of numbers enclosed in single quotes
 - Default: ' '
 
### discordBotToken
- This is the token of the discord bot that you made to use for this plugin.
- If you do not know how to make a discord bot, here is a good tutourial (https://www.digitaltrends.com/gaming/how-to-make-a-discord-bot/) follow steps 1-4.
- This should be a string of numbers and letters
- WARNING: DO NOT SHARE YOUR BOTS TOKEN WITH ANYONE. TREAT IT LIKE YOUR DISCORD PASSWORD.
- Default: ' '

### discordWelcomeChannelID
 - This is the channel id of the channel used by the /discord command to create an invite
 - It can be the same as your minecraft channel
 - This should be a string of numbers enclosed i single quotes
 - Default: ' '

## Extra Functionality
### discordAdminChannelID
 - The channel id of the channel used to log andmin info, not required but suggested
 - To get this id, make sure developer mode is turned on in discord, right-click on the text channel, and select "Copy ID"
 - This should be a string of numbers enclosed in single quotes
 - Default: ' '
 
### discordOnlineRoleName
 - This is the name of the online role you set up in the tutorial.
 - Default: online in-game
 
### logDeathInfoInAdminChannel
 - A boolean that decides if extra death info should be logged in the Discord admin channel.
 - Default: true
 
### discordAdminChannelEnabled
 - A boolean that decides if the Discord admin channel should be enabled.
 - Default: false
 
## Toggleable
### sendDeathMessagesToDiscord
- A boolean that decides if death messages should be sent to Discord.
- Default: true

### discordCommandEnabled
- Self Explaintory
- Default: true

## discordJoinLeaveMessagesEnabled
- A boolean that decides if join/leave messages should be sent to discord
- Default: true

## Customization
### customDiscordBotStatus
 - The status message of your discord bot!
 - Default: 'Minecraft! #Kanagroobot is playing Minecraft!'
 
### customDiscordMessageOnStartup
 - The message that will be sent to Discord when the server starts.
 - Default: Server online! Join now!
 
### customDiscordMessageOnShutdown
 - The message that will be sent to Discord when the server stops.
 - Default: Server Stopped.
 
### customDiscordPlayerJoinMessage
- The message that will be sent to Discord when a player joins the server.
- Make sure to enclose it in single quotes and include a space like the default
- Default: ' has joined the server'

### customDiscordPlayerLeaveMessage
- The message that will be sent to Discord when a player leaves the server.
- Make sure to enclose it in single quotes and include a space like the default
- Default: ' has left the server'

## Should probably be in a different plugin
### minecraftJoinMessageEnabled
- Should a custom message be sent in Minecarft whenever a player joins.
- Default: false

### minecraftJoinMessage
- The message that will be sent in Minecraft when a player joins the server.
- Make sure to enclose it in single quotes and include a space like the default
- Default: ' has joined the game!'

### minecraftBackCommandEnabled
- Enables/Disables the /back command.
- Default: false

### minecraftCenterDeathMessages
- Enables/Disables cool centered death messages.
- Deafult: false

### minecraftWelcomeMessageEnabled
- Enables/Disables the custom welcome message that sends in Minecraft whenever a new player joins
- Default: false

## Dev Tools
### debugEnabled
- Enables/Disables debug mode.
- Default: false.

### discordDebugGuildID
- The id of your server used for debugging.
- Only put something here if you need to do dev shit.
- Default: ' '

## discordDebugChannelID
- The channel id of your debug channel.
- Only put the id here if you need to do dev shit.
- Default: ' '

## Death Messages
### deathMessages
- A list of custom death messages to use.
- Format:
    - "- [your text here]"
 - Defaults:
    - is no longer with us
    - passed away
    - slipped in bbq sauce
    - ate way too much rice
