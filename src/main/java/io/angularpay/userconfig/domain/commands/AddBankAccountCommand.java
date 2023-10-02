package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.BankAccount;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.AddBankAccountCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;
import static io.angularpay.userconfig.helpers.CommandHelper.validRequestStatusOrThrow;

@Service
public class AddBankAccountCommand extends AbstractCommand<AddBankAccountCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public AddBankAccountCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("AddBankAccountCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(AddBankAccountCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(AddBankAccountCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        BankAccount bankAccount = BankAccount.builder()
                .reference(UUID.randomUUID().toString())
                .integrationType(request.getBankAccountApiModel().getIntegrationType())
                .integrationReference(request.getBankAccountApiModel().getIntegrationReference())
                .accountNumber(request.getBankAccountApiModel().getAccountNumber())
                .accountName(request.getBankAccountApiModel().getAccountName())
                .iban(request.getBankAccountApiModel().getIban())
                .bankReference(request.getBankAccountApiModel().getBankReference())
                .build();
        this.commandHelper.addItemToCollection(found, bankAccount, found::getBankAccounts, found::setBankAccounts);
        return TreeReferenceResponse.builder().userReference(found.getReference()).itemReference(bankAccount.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(AddBankAccountCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Collections.emptyList();
    }

    @Override
    public ResourceReferenceResponse map(TreeReferenceResponse treeReferenceResponse) {
        return new ResourceReferenceResponse(treeReferenceResponse.getItemReference());
    }
}
