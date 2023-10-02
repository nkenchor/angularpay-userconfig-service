package io.angularpay.userconfig.adapters.inbound;

import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.domain.UserProfile;
import io.angularpay.userconfig.domain.commands.*;
import io.angularpay.userconfig.models.*;
import io.angularpay.userconfig.ports.inbound.RestApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static io.angularpay.userconfig.helpers.Helper.fromHeaders;

@RestController
@RequestMapping("/user-configuration/accounts")
@RequiredArgsConstructor
public class RestApiAdapter implements RestApiPort {

    private final AngularPayConfiguration configuration;

    private final CreateUserConfigurationCommand createUserConfigurationCommand;
    private final EnableUserConfigurationCommand enableUserConfigurationCommand;
    private final UpdateUserProfileCommand updateUserProfileCommand;
    private final DeleteUserConfigurationCommand deleteUserConfigurationCommand;
    private final AddAddressCommand addAddressCommand;
    private final UpdateAddressCommand updateAddressCommand;
    private final DeleteAddressCommand deleteAddressCommand;
    private final EnableTwoFactorAuthenticationCommand enableTwoFactorAuthenticationCommand;
    private final EnableTransactionPinCommand enableTransactionPinCommand;
    private final AddBankAccountCommand addBankAccountCommand;
    private final UpdateBankAccountCommand updateBankAccountCommand;
    private final DeleteBankAccountCommand deleteBankAccountCommand;
    private final AddNewsfeedPreferenceCommand addNewsfeedPreferenceCommand;
    private final UpdateNewsfeedPreferenceCommand updateNewsfeedPreferenceCommand;
    private final AddServiceSubscriptionCommand addServiceSubscriptionCommand;
    private final UpdateServiceSubscriptionCommand updateServiceSubscriptionCommand;
    private final SetSmartSaveConfigurationCommand setSmartSaveConfigurationCommand;
    private final UpdateNotificationOptionsCommand updateNotificationOptionsCommand;
    private final UpdateIncomeCommand updateIncomeCommand;
    private final AddKycIdentificationCommand addKycIdentificationCommand;
    private final UpdateKycIdentificationCommand updateKycIdentificationCommand;
    private final DeleteKycIdentificationCommand deleteKycIdentificationCommand;
    private final UpdateKycStatusCommand updateKycStatusCommand;
    private final GetUserConfigurationByReferenceCommand getUserConfigurationByReferenceCommand;
    private final GetUserConfigurationByEmailCommand getUserConfigurationByEmailCommand;
    private final GetUserConfigurationByPhoneCommand getUserConfigurationByPhoneCommand;
    private final GetUserConfigurationListCommand getUserConfigurationListCommand;
    private final GetUserProfileByReferenceCommand getUserProfileByReferenceCommand;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse create(
            @RequestBody CreateUserConfigurationRequest request,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        CreateUserConfigurationCommandRequest createUserConfigurationCommandRequest = CreateUserConfigurationCommandRequest.builder()
                .createUserConfigurationRequest(request)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.createUserConfigurationCommand.execute(createUserConfigurationCommandRequest);
    }

    @PutMapping("/{userReference}/enable/{enable}")
    @Override
    public void enableUser(
            @PathVariable String userReference,
            @PathVariable Boolean enable,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        EnableUserConfigurationCommandRequest enableUserConfigurationCommandRequest = EnableUserConfigurationCommandRequest.builder()
                .userReference(userReference)
                .enabled(enable)
                .authenticatedUser(authenticatedUser)
                .build();
        this.enableUserConfigurationCommand.execute(enableUserConfigurationCommandRequest);
    }

    @PutMapping("/{userReference}/profile")
    @Override
    public void updateUserProfile(
            @PathVariable String userReference,
            @RequestBody UserProfileApiModel userProfileApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateUserProfileCommandRequest updateUserProfileCommandRequest = UpdateUserProfileCommandRequest.builder()
                .userReference(userReference)
                .userProfileApiModel(userProfileApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateUserProfileCommand.execute(updateUserProfileCommandRequest);
    }

    @DeleteMapping("/{userReference}")
    @Override
    public void deleteUserConfiguration(
            @PathVariable String userReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        DeleteUserConfigurationCommandRequest deleteUserConfigurationCommandRequest = DeleteUserConfigurationCommandRequest.builder()
                .userReference(userReference)
                .authenticatedUser(authenticatedUser)
                .build();
        this.deleteUserConfigurationCommand.execute(deleteUserConfigurationCommandRequest);
    }

    @PostMapping("/{userReference}/addresses")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse addAddress(
            @PathVariable String userReference,
            @RequestBody AddressApiModel addressApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        AddAddressCommandRequest addAddressCommandRequest = AddAddressCommandRequest.builder()
                .userReference(userReference)
                .addressApiModel(addressApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.addAddressCommand.execute(addAddressCommandRequest);
    }

    @PutMapping("/{userReference}/addresses/{addressReference}")
    @Override
    public void updateAddress(
            @PathVariable String userReference,
            @PathVariable String addressReference,
            @RequestBody AddressApiModel addressApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateAddressCommandRequest updateAddressCommandRequest = UpdateAddressCommandRequest.builder()
                .userReference(userReference)
                .addressReference(addressReference)
                .addressApiModel(addressApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateAddressCommand.execute(updateAddressCommandRequest);
    }

    @DeleteMapping("/{userReference}/addresses/{addressReference}")
    @Override
    public void deleteAddress(
            @PathVariable String userReference,
            @PathVariable String addressReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        DeleteAddressCommandRequest deleteAddressCommandRequest = DeleteAddressCommandRequest.builder()
                .userReference(userReference)
                .addressReference(addressReference)
                .authenticatedUser(authenticatedUser)
                .build();
        this.deleteAddressCommand.execute(deleteAddressCommandRequest);
    }

    @PutMapping("/{userReference}/security/2FA/enable/{enable}")
    @Override
    public void enableTwoFactorAuthentication(
            @PathVariable String userReference,
            @PathVariable Boolean enable,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        EnableTwoFactorAuthenticationCommandRequest enableTwoFactorAuthenticationCommandRequest = EnableTwoFactorAuthenticationCommandRequest.builder()
                .userReference(userReference)
                .enabled(enable)
                .authenticatedUser(authenticatedUser)
                .build();
        this.enableTwoFactorAuthenticationCommand.execute(enableTwoFactorAuthenticationCommandRequest);
    }

    @PutMapping("/{userReference}/security/transaction-pin/enable/{enable}")
    @Override
    public void enableTransactionPin(
            @PathVariable String userReference,
            @PathVariable Boolean enable,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        EnableTransactionPinCommandRequest enableTransactionPinCommandRequest = EnableTransactionPinCommandRequest.builder()
                .userReference(userReference)
                .enabled(enable)
                .authenticatedUser(authenticatedUser)
                .build();
        this.enableTransactionPinCommand.execute(enableTransactionPinCommandRequest);
    }

    @PostMapping("/{userReference}/bank-accounts")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse addBankAccount(
            @PathVariable String userReference,
            @RequestBody BankAccountApiModel bankAccountApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        AddBankAccountCommandRequest addBankAccountCommandRequest = AddBankAccountCommandRequest.builder()
                .userReference(userReference)
                .bankAccountApiModel(bankAccountApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.addBankAccountCommand.execute(addBankAccountCommandRequest);
    }

    @PutMapping("/{userReference}/bank-accounts/{bankAccountReference}")
    @Override
    public void updateBankAccount(
            @PathVariable String userReference,
            @PathVariable String bankAccountReference,
            @RequestBody BankAccountApiModel bankAccountApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateBankAccountCommandRequest updateBankAccountCommandRequest = UpdateBankAccountCommandRequest.builder()
                .userReference(userReference)
                .bankAccountReference(bankAccountReference)
                .bankAccountApiModel(bankAccountApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateBankAccountCommand.execute(updateBankAccountCommandRequest);
    }

    @DeleteMapping("/{userReference}/bank-accounts/{bankAccountReference}")
    @Override
    public void deleteBankAccount(
            @PathVariable String userReference,
            @PathVariable String bankAccountReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        DeleteBankAccountCommandRequest deleteBankAccountCommandRequest = DeleteBankAccountCommandRequest.builder()
                .userReference(userReference)
                .bankAccountReference(bankAccountReference)
                .authenticatedUser(authenticatedUser)
                .build();
        this.deleteBankAccountCommand.execute(deleteBankAccountCommandRequest);
    }

    @PostMapping("/{userReference}/newsfeed-preferences")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse addNewsfeedPreference(
            @PathVariable String userReference,
            @RequestBody NewsfeedPreferenceApiModel newsfeedPreferenceApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        AddNewsfeedPreferenceCommandRequest addNewsfeedPreferenceCommandRequest = AddNewsfeedPreferenceCommandRequest.builder()
                .userReference(userReference)
                .newsfeedPreferenceApiModel(newsfeedPreferenceApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.addNewsfeedPreferenceCommand.execute(addNewsfeedPreferenceCommandRequest);
    }

    @PutMapping("/{userReference}/newsfeed-preferences/{serviceReference}")
    @Override
    public void updateNewsfeedPreference(
            @PathVariable String userReference,
            @PathVariable String serviceReference,
            @RequestBody NewsfeedPreferenceApiModel newsfeedPreferenceApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateNewsfeedPreferenceCommandRequest updateNewsfeedPreferenceCommandRequest = UpdateNewsfeedPreferenceCommandRequest.builder()
                .userReference(userReference)
                .serviceReference(serviceReference)
                .newsfeedPreferenceApiModel(newsfeedPreferenceApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateNewsfeedPreferenceCommand.execute(updateNewsfeedPreferenceCommandRequest);
    }

    @PostMapping("/{userReference}/service-configurations")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse addServiceConfiguration(
            @PathVariable String userReference,
            @RequestBody AddServiceConfigurationApiModel addServiceConfigurationApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        AddServiceConfigurationCommandRequest addServiceConfigurationCommandRequest = AddServiceConfigurationCommandRequest.builder()
                .userReference(userReference)
                .addServiceConfigurationApiModel(addServiceConfigurationApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.addServiceSubscriptionCommand.execute(addServiceConfigurationCommandRequest);
    }

    @PutMapping("/{userReference}/service-configurations/{serviceReference}")
    @Override
    public void updateServiceConfiguration(
            @PathVariable String userReference,
            @PathVariable String serviceReference,
            @RequestBody UpdateServiceConfigurationApiModel updateServiceConfigurationApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateServiceConfigurationCommandRequest updateServiceConfigurationCommandRequest = UpdateServiceConfigurationCommandRequest.builder()
                .userReference(userReference)
                .serviceReference(serviceReference)
                .updateServiceConfigurationApiModel(updateServiceConfigurationApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateServiceSubscriptionCommand.execute(updateServiceConfigurationCommandRequest);
    }

    @PutMapping("/{userReference}/smart-save-configuration")
    @Override
    public void setSmartSaveConfiguration(
            @PathVariable String userReference,
            @RequestBody SmartSaveConfigurationApiModel smartSaveConfigurationApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        SetSmartSaveConfigurationCommandRequest setSmartSaveConfigurationCommandRequest = SetSmartSaveConfigurationCommandRequest.builder()
                .userReference(userReference)
                .smartSaveConfigurationApiModel(smartSaveConfigurationApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.setSmartSaveConfigurationCommand.execute(setSmartSaveConfigurationCommandRequest);
    }

    @PutMapping("/{userReference}/notification")
    @Override
    public void updateNotificationOptions(
            @PathVariable String userReference,
            @RequestBody NotificationOptionsApiModel notificationOptionsApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateNotificationOptionsCommandRequest updateNotificationOptionsCommandRequest = UpdateNotificationOptionsCommandRequest.builder()
                .userReference(userReference)
                .notificationOptionsApiModel(notificationOptionsApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateNotificationOptionsCommand.execute(updateNotificationOptionsCommandRequest);
    }

    @PutMapping("/{userReference}/income")
    @Override
    public void updateIncome(
            @PathVariable String userReference,
            @RequestBody IncomeApiModel incomeApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateIncomeCommandRequest updateKycIdentificationCommandRequest = UpdateIncomeCommandRequest.builder()
                .userReference(userReference)
                .incomeApiModel(incomeApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateIncomeCommand.execute(updateKycIdentificationCommandRequest);
    }

    @PostMapping("/{userReference}/kyc-identifications")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse addKycIdentification(
            @PathVariable String userReference,
            @RequestBody KycIdentificationApiModel kycIdentificationApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        AddKycIdentificationCommandRequest addKycIdentificationCommandRequest = AddKycIdentificationCommandRequest.builder()
                .userReference(userReference)
                .kycIdentificationApiModel(kycIdentificationApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.addKycIdentificationCommand.execute(addKycIdentificationCommandRequest);
    }

    @PutMapping("/{userReference}/kyc-identifications/{identificationReference}")
    @Override
    public void updateKycIdentification(
            @PathVariable String userReference,
            @PathVariable String identificationReference,
            @RequestBody KycIdentificationApiModel kycIdentificationApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateKycIdentificationCommandRequest updateKycIdentificationCommandRequest = UpdateKycIdentificationCommandRequest.builder()
                .userReference(userReference)
                .identificationReference(identificationReference)
                .kycIdentificationApiModel(kycIdentificationApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateKycIdentificationCommand.execute(updateKycIdentificationCommandRequest);
    }

    @DeleteMapping("/{userReference}/kyc-identifications/{identificationReference}")
    @Override
    public void deleteKycIdentification(
            @PathVariable String userReference,
            @PathVariable String identificationReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        DeleteKycIdentificationCommandRequest deleteKycIdentificationCommandRequest = DeleteKycIdentificationCommandRequest.builder()
                .userReference(userReference)
                .identificationReference(identificationReference)
                .authenticatedUser(authenticatedUser)
                .build();
        this.deleteKycIdentificationCommand.execute(deleteKycIdentificationCommandRequest);
    }

    @PutMapping("/{userReference}/kyc-status")
    @Override
    public void updateKycStatus(
            @PathVariable String userReference,
            @RequestBody KycStatusApiModel kycStatusApiModel,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        UpdateKycStatusCommandRequest updateKycStatusCommandRequest = UpdateKycStatusCommandRequest.builder()
                .userReference(userReference)
                .kycStatusApiModel(kycStatusApiModel)
                .authenticatedUser(authenticatedUser)
                .build();
        this.updateKycStatusCommand.execute(updateKycStatusCommandRequest);
    }

    @GetMapping("/{userReference}")
    @ResponseBody
    @Override
    public UserConfiguration getRequestByReference(
            @PathVariable String userReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GenericGetByReferenceCommandRequest genericGetByReferenceCommandRequest = GenericGetByReferenceCommandRequest.builder()
                .userReference(userReference)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.getUserConfigurationByReferenceCommand.execute(genericGetByReferenceCommandRequest);
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    @Override
    public UserConfiguration getRequestByEmail(
            @PathVariable String email,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GetByEmailCommandRequest getByEmailCommandRequest = GetByEmailCommandRequest.builder()
                .email(email)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.getUserConfigurationByEmailCommand.execute(getByEmailCommandRequest);
    }

    @GetMapping("/phone/{phone}")
    @ResponseBody
    @Override
    public UserConfiguration getRequestByPhone(
            @PathVariable String phone,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GetByPhoneCommandRequest getByPhoneCommandRequest = GetByPhoneCommandRequest.builder()
                .phone(phone)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.getUserConfigurationByPhoneCommand.execute(getByPhoneCommandRequest);
    }

    @GetMapping("/list/page/{page}")
    @ResponseBody
    @Override
    public List<UserConfiguration> getUserConfigurationList(
            @PathVariable int page,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GetUserConfigurationListCommandRequest getNewsfeedCommandRequest = GetUserConfigurationListCommandRequest.builder()
                .authenticatedUser(authenticatedUser)
                .paging(Paging.builder().size(this.configuration.getPageSize()).index(page).build())
                .build();
        return this.getUserConfigurationListCommand.execute(getNewsfeedCommandRequest);
    }

    @GetMapping("/{userReference}/profile")
    @ResponseBody
    @Override
    public UserProfile getUserProfile(
            @PathVariable String userReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GenericGetByReferenceCommandRequest genericGetByReferenceCommandRequest = GenericGetByReferenceCommandRequest.builder()
                .userReference(userReference)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.getUserProfileByReferenceCommand.execute(genericGetByReferenceCommandRequest);
    }

}
