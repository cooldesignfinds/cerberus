package com.nike.cerberus.controller;

import static com.nike.cerberus.security.CerberusPrincipal.ROLE_USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import com.nike.cerberus.domain.SafeDepositBoxSummary;
import com.nike.cerberus.domain.SafeDepositBoxV2;
import com.nike.cerberus.security.CerberusPrincipal;
import com.nike.cerberus.security.PrincipalHasOwnerPermsForSdb;
import com.nike.cerberus.security.PrincipalHasReadPermsForSdb;
import com.nike.cerberus.service.SafeDepositBoxService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Validated
@RestController
@RequestMapping("/v2/safe-deposit-box")
public class SafeDepositBoxControllerV2 {

  private final SafeDepositBoxService safeDepositBoxService;

  @Autowired
  public SafeDepositBoxControllerV2(SafeDepositBoxService safeDepositBoxService) {
    this.safeDepositBoxService = safeDepositBoxService;
  }

  @RolesAllowed(ROLE_USER)
  @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<SafeDepositBoxV2> createSafeDepositBox(
      @RequestBody SafeDepositBoxV2 request,
      Authentication authentication,
      UriComponentsBuilder b) {

    var sdb = safeDepositBoxService.createSafeDepositBoxV2(request, authentication.getName());
    var url = b.path("/V2/safe-deposit-box/{id}").buildAndExpand(sdb.getId()).toUri();
    return ResponseEntity.created(url).body(sdb);
  }

  @PrincipalHasReadPermsForSdb
  @RequestMapping(value = "/{sdbId:.+}", method = GET)
  public SafeDepositBoxV2 getSafeDepositBox(@PathVariable("sdbId") String sdbId) {
    return safeDepositBoxService.getSDBAndValidatePrincipalAssociationV2(sdbId);
  }

  @PrincipalHasOwnerPermsForSdb
  @RequestMapping(value = "/{sdbId:.+}", method = PUT)
  public SafeDepositBoxV2 updateSafeDepositBox(
      @PathVariable("sdbId") String sdbId,
      @RequestBody SafeDepositBoxV2 request,
      Authentication authentication) {
    return safeDepositBoxService.updateSafeDepositBoxV2(
        request, (CerberusPrincipal) authentication, sdbId);
  }

  @PrincipalHasOwnerPermsForSdb
  @RequestMapping(value = "/{sdbId:.+}", method = DELETE)
  public void deleteSafeDepositBox(@PathVariable("sdbId") String sdbId) {
    safeDepositBoxService.deleteSafeDepositBox(sdbId);
  }

  @RolesAllowed(ROLE_USER)
  @RequestMapping(method = GET)
  public List<SafeDepositBoxSummary> getSafeDepositBoxes(Authentication authentication) {
    return safeDepositBoxService.getAssociatedSafeDepositBoxes((CerberusPrincipal) authentication);
  }
}