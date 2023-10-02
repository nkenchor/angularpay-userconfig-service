package io.angularpay.userconfig.ports.inbound;

import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.domain.UserProfile;
import io.angularpay.userconfig.models.*;

import java.util.List;
import java.util.Map;

public interface RestApiPort {
    GenericReferenceResponse create(CreateUserConfigurationRequest request, Map<String, String> headers);
    void enableUser(String userReference, Boolean enable, Map<String, String> headers);
    void updateUserProfile(String userReference, UserProfileApiModel userProfileApiModel, Map<String, String> headers);
    void deleteUserConfiguration(String userReference, Map<String, String> headers);
    GenericReferenceResponse addAddress(String userReference, AddressApiModel addressApiModel, Map<String, String> headers);
    void updateAddress(String userReference, String addressReference, AddressApiModel addressApiModel, Map<String, String> headers);
    void deleteAddress(String userReference, String addressReference, Map<String, String> headers);
    void enableTwoFactorAuthentication(String userReference, Boolean enable, Map<String, String> headers);
    void enableTransactionPin(String userReference, Boolean enable, Map<String, String> headers);
    GenericReferenceResponse addBankAccount(String userReference, BankAccountApiModel bankAccountApiModel, Map<String, String> headers);
    void updateBankAccount(String userReference, String bankAccountReference, BankAccountApiModel bankAccountApiModel, Map<String, String> headers);
    void deleteBankAccount(String userReference, String bankAccountReference, Map<String, String> headers);
    GenericReferenceResponse addNewsfeedPreference( String userReference, NewsfeedPreferenceApiModel newsfeedPreferenceApiModel, Map<String, String> headers);
    void updateNewsfeedPreference(String userReference, String serviceReference, NewsfeedPreferenceApiModel newsfeedPreferenceApiModel, Map<String, String> headers);
    GenericReferenceResponse addServiceConfiguration(String userReference, AddServiceConfigurationApiModel addServiceConfigurationApiModel, Map<String, String> headers);
    void updateServiceConfiguration(String userReference, String serviceReference, UpdateServiceConfigurationApiModel updateServiceConfigurationApiModel, Map<String, String> headers);
    void setSmartSaveConfiguration(String userReference, SmartSaveConfigurationApiModel smartSaveConfigurationApiModel, Map<String, String> headers);
    void updateNotificationOptions(String userReference, NotificationOptionsApiModel notificationOptionsApiModel, Map<String, String> headers);
    void updateIncome(String userReference, IncomeApiModel incomeApiModel, Map<String, String> headers);
    GenericReferenceResponse addKycIdentification(String userReference, KycIdentificationApiModel kycIdentificationApiModel, Map<String, String> headers);
    void updateKycIdentification(String userReference, String identificationReference, KycIdentificationApiModel kycIdentificationApiModel, Map<String, String> headers);
    void deleteKycIdentification(String userReference, String identificationReference, Map<String, String> headers);
    void updateKycStatus(String userReference, KycStatusApiModel kycStatusApiModel, Map<String, String> headers);
    UserConfiguration getRequestByReference(String userReference, Map<String, String> headers);
    UserConfiguration getRequestByEmail(String email, Map<String, String> headers);
    UserConfiguration getRequestByPhone(String phone, Map<String, String> headers);
    List<UserConfiguration> getUserConfigurationList(int page, Map<String, String> headers);
    UserProfile getUserProfile(String userReference, Map<String, String> headers);
}
