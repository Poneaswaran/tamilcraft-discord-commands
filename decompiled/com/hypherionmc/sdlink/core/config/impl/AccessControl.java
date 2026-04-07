/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path
 *  com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment
 */
package com.hypherionmc.sdlink.core.config.impl;

import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.Path;
import com.hypherionmc.craterlib.libs.moonconfig.core.conversion.SpecComment;
import java.util.ArrayList;
import java.util.List;

public final class AccessControl {
    @Path(value="enabled")
    @SpecComment(value="Enable Access Control")
    public boolean enabled = false;
    @Path(value="optionalVerification")
    @SpecComment(value="Allow users to verify their accounts without access control. This setting is ignored if the above setting is set to true")
    public boolean optionalVerification = false;
    @Path(value="allowVerifyInDm")
    @SpecComment(value="Allows verification and un-verification via DM")
    public boolean allowVerifyInDm = true;
    @Path(value="requireDiscordMembership")
    @SpecComment(value="Does the player need to be a member of your discord to join")
    public boolean requireDiscordMembership = false;
    @Path(value="allowMultipleAccounts")
    @SpecComment(value="Can players verify multiple Minecraft Accounts")
    public boolean allowMultipleAccounts = false;
    @Path(value="changeDiscordNickname")
    @SpecComment(value="Change the discord user nickname to their Minecraft name when their accounts are linked")
    public boolean changeDiscordNickname = false;
    @Path(value="requiredRoles")
    @SpecComment(value="Optional: The player requires any of these roles to be able to join your server")
    public List<String> requiredRoles = new ArrayList<String>();
    @Path(value="deniedRoles")
    @SpecComment(value="Optional: Players with these roles will never be allowed access to your server")
    public List<String> deniedRoles = new ArrayList<String>();
    @Path(value="verifiedRole")
    @SpecComment(value="Optional: Role name or ID to assign to verified player accounts")
    public List<String> verifiedRole = new ArrayList<String>();
    @Path(value="verificationMessages")
    @SpecComment(value="Configure messages shown to players when they don't meet verification requirements")
    public AccessMessages verificationMessages = new AccessMessages();
    @Path(value="banPlayerOnDiscordBan")
    @SpecComment(value="Should players with verified accounts, be banned from Minecraft if they get banned on discord")
    public boolean banPlayerOnDiscordBan = false;
    @Path(value="banMemberOnMinecraftBan")
    @SpecComment(value="Should members with verified accounts, be banned from discord when they are banned on Minecraft")
    public boolean banMemberOnMinecraftBan = false;

    public static class AccessMessages {
        @Path(value="optionalVerificationMessage")
        @SpecComment(value="This message is shown to users when they use the in-game verification command")
        public String optionalVerificationMessage = "Your verification code is: {code}. Please DM our bot, or use the /verify command in our discord to verify your account";
        @Path(value="accountVerification")
        @SpecComment(value="The message shown to players that are not verified")
        public String accountVerify = "This server requires account verification. Your verification code is: {code}. Please visit our discord server for instructions on how to verify your account.";
        @Path(value="nonMember")
        @SpecComment(value="Message to show to players that are not a member of your discord")
        public String nonMember = "Sorry, you need to be a member of our discord server to join this server";
        @Path(value="requireRoles")
        @SpecComment(value="Message to show when player doesn't have one of the required roles. Use {roles} to display the names of configured roles")
        public String requireRoles = "Sorry, but you require any of the following roles: {roles}";
        @Path(value="roleDenied")
        @SpecComment(value="Message to show when player has a role from the deniedRoles list")
        public String roleDenied = "Sorry, but you are not allowed to access this server.";
    }
}

