package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.NewsfeedPreference;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.AddNewsfeedPreferenceCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.*;

@Service
public class AddNewsfeedPreferenceCommand extends AbstractCommand<AddNewsfeedPreferenceCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public AddNewsfeedPreferenceCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("AddNewsfeedPreferenceCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(AddNewsfeedPreferenceCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(AddNewsfeedPreferenceCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        validateUniqueNewsfeedPreferenceOrThrow(request.getNewsfeedPreferenceApiModel().getServiceReference(), found);
        NewsfeedPreference newsfeedPreference = NewsfeedPreference.builder()
                .serviceReference(request.getNewsfeedPreferenceApiModel().getServiceReference())
                .showInNewsfeed(request.getNewsfeedPreferenceApiModel().getShowInNewsfeed())
                .onlyShowStatusTypes(request.getNewsfeedPreferenceApiModel().getOnlyShowStatusTypes())
                .build();
        this.commandHelper.addItemToCollection(found, newsfeedPreference, found::getNewsfeedPreferences, found::setNewsfeedPreferences);
        return TreeReferenceResponse.builder().userReference(found.getReference()).itemReference(newsfeedPreference.getServiceReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(AddNewsfeedPreferenceCommandRequest request) {
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
